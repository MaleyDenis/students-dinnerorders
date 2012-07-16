package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.exception.IllegalUserLoginException;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;

public class LdapService {
    private Hashtable environment = new Hashtable();
    private final String settings = "com.sun.jndi.ldap.LdapCtxFactory";
    private final String ldapURL;

    public LdapService(String URL) {
        ldapURL = URL;
        environment.put(Context.INITIAL_CONTEXT_FACTORY, settings);
        environment.put(Context.PROVIDER_URL, ldapURL);
    }

    private NamingEnumeration getAttributes() {
        NamingEnumeration result = null;
        try {
            DirContext dirContext = new InitialDirContext(environment);
            SearchControls controls = createSearchControls();
            String searchFilter = "(objectclass=person)";
            String startSearchBase = "ou=addressbook";
            result = dirContext.search(startSearchBase, searchFilter, controls);
            dirContext.close();
        } catch (NamingException namingException) {
            namingException.printStackTrace();
        }
        return result;
    }

    private SearchControls createSearchControls() {
        String[] searchAttributes = {"uid"};
        SearchControls controls = new SearchControls();
        controls.setReturningAttributes(searchAttributes);
        controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        return controls;
    }

    public boolean checkUser(String login, String password) {
        if (login == null) {
            throw new NullPointerException();
        }
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
                if (checkAllAttributes(resultUnitAttributes, login)) {
                    return resultUnit.getName().substring(("cn=").length());
                }
            }
        } catch (NamingException namingException) {
            namingException.printStackTrace();
        }
        return null;
    }

    private boolean checkAllAttributes(Attributes resultUnitAttributes, String login) {
        try {
            Attribute attribute = resultUnitAttributes.get("uid");
            int amount = attribute.size();
            for (int i = 0; i < amount; i++){
                if (attribute.get(i).equals(login)) {
                    return true;
                }
            }
        } catch (NamingException namingException) {
            namingException.printStackTrace();
        }
        return false;
    }

    public String getUserName(String login) {
        String userName = isLoginExist(login);
        if (login == null) {
            throw new IllegalUserLoginException(login);
        } else
            return userName;
    }
}