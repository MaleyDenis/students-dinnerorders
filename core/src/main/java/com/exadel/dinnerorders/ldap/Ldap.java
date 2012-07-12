package com.exadel.dinnerorders.ldap;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.util.Hashtable;

public class Ldap {
    private Hashtable environment = new Hashtable();
    private final String settings = "com.sun.jndi.ldap.LdapCtxFactory";
    private String ldapURL;

    public Ldap(String serverURL){
        ldapURL = serverURL;
        environment.put(Context.INITIAL_CONTEXT_FACTORY, settings);
        environment.put(Context.PROVIDER_URL, ldapURL);
        environment.put(Context.SECURITY_AUTHENTICATION, "simple");
        environment.put(Context.SECURITY_PRINCIPAL,"cn=LDAPaccess,ou=Special,dc=exadel,dc=com");
        environment.put(Context.SECURITY_CREDENTIALS,"EltegrA");
    }

    private NamingEnumeration getAttributes() {
        try {
            String[] retAttributes = {"cu", "secret"};
            DirContext dirContext = new InitialDirContext(environment);
            SearchControls controls = createSearchControls();
            controls.setReturningAttributes(retAttributes);
            controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String searchFilter = "(objectclass=person)";
            String startSearchBase = "ou=addressbook";
            return dirContext.search(startSearchBase, searchFilter, controls);
        } catch (NamingException namingException) {
            namingException.printStackTrace();
        }
        return null;
    }

    private SearchControls createSearchControls() {
        String[] searchAttributes = {"cn", "secret"};
        SearchControls controls = new SearchControls();
        controls.setReturningAttributes(searchAttributes);
        controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        return controls;
    }

    public boolean searchUser(String login, String password) {
        try {
            NamingEnumeration searchResult = getAttributes();
            while ( searchResult.hasMore() ){
                SearchResult resultUnit = (SearchResult)searchResult.next();
                Attributes resultUnitAttributes = resultUnit.getAttributes();
                if (resultUnitAttributes.get("cn").get().equals(login)) {
                    return resultUnitAttributes.get("secret").equals(password);
                }
            }
        } catch (NamingException namingException) {
            namingException.printStackTrace();
        }
        throw new IllegalUserLoginException(login);
    }
}
