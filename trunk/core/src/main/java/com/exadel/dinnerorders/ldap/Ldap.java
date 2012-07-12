package com.exadel.dinnerorders.ldap;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;

public class Ldap {
    private Hashtable environment = new Hashtable();
    private final String settings = "com.sun.jndi.ldap.LdapCtxFactory";
    private final String ldapURL;

    public Ldap(String serverURL){
        ldapURL = serverURL;
        environment.put(Context.INITIAL_CONTEXT_FACTORY, settings);
        environment.put(Context.PROVIDER_URL, ldapURL);
    }

    private NamingEnumeration getAttributes() {
        try {
            DirContext dirContext = new InitialDirContext(environment);
            SearchControls controls = createSearchControls();
            String searchFilter = "(objectclass=person)";
            String startSearchBase = "ou=addressbook";
            return dirContext.search(startSearchBase, searchFilter, controls);
        } catch (NamingException namingException) {
            namingException.printStackTrace();
        }
        return null;
    }

    private SearchControls createSearchControls() {
        String[] searchAttributes = {"uid"};
        SearchControls controls = new SearchControls();
        controls.setReturningAttributes(searchAttributes);
        controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        return controls;
    }

    public boolean checkUser(String login, String password) {
        String userCN = isLoginExist(login);
        if ( userCN == null) {
            throw new IllegalUserLoginException(login);
        }
        try {
            environment.put(Context.SECURITY_AUTHENTICATION, "simple");
            environment.put(Context.SECURITY_PRINCIPAL,"cn=" + userCN + ",ou=addressbook,dc=exadel,dc=com");
            environment.put(Context.SECURITY_CREDENTIALS, password);
            DirContext dirContext = new InitialDirContext(environment);
            dirContext.close();
        } catch (NamingException namingException) {
            namingException.printStackTrace();
            return false;
        }
        return true;
    }

    private String isLoginExist(String login) {
        try {
            NamingEnumeration searchResult = getAttributes();
            while ( searchResult.hasMore() ){
                SearchResult resultUnit = (SearchResult)searchResult.next();
                Attributes resultUnitAttributes = resultUnit.getAttributes();
                if (resultUnitAttributes.get("uid").get().equals(login)) {
                    return resultUnit.getName().substring(("cn=").length());
                }
            }
        } catch (NamingException namingException) {
            namingException.printStackTrace();
        }
        return null;
    }
}