package com.carfax.services
import java.security.Security;

import org.springframework.security.access.AccessDecisionManager
import org.springframework.security.access.vote.*
import org.springframework.security.core.Authentication
import org.springframework.security.core.session.*
import org.springframework.security.access.*
import org.springframework.security.core.context.*

/**
 * @author Marcin Muras
 */
public class SecurityService {
	// spring security session registry. it is used to check if given user is logged.
	private SessionRegistry sessionRegistry;
	private List voters = new ArrayList();
	

	public SecurityService() {
		// role name voter
		voters.add(new RoleVoter());
		// IS_AUTHENTICATED_FULLY, IS_AUTHENTICATED_ANONYMOUSLY and IS_AUTHENTICATED_REMEMBERED voter.
		voters.add(new AuthenticatedVoter());
	}

	public boolean ifAllGranted(String rolesString) {
		AbstractAccessDecisionManager accessDecisionManager = new UnanimousBased();
		accessDecisionManager.setDecisionVoters(voters);
		return secure(null, rolesString, accessDecisionManager);
		//return genericGranted(rolesString, SecurityRule.ALL);
	}

	public boolean ifAnyGranted(String rolesString) {
		AbstractAccessDecisionManager accessDecisionManager = new AffirmativeBased();
		accessDecisionManager.setDecisionVoters(voters);
		return secure(null, rolesString, accessDecisionManager);
		//return genericGranted(rolesString, SecurityRule.ANY);
	}

	public boolean ifNotGranted(String rolesString) {
		return !ifAnyGranted(rolesString);
	}

	public boolean isNotLoggedIn() {
		return ifNotGranted(AuthenticatedVoter.IS_AUTHENTICATED_FULLY);
	}

	public boolean isLoggedIn() {
		return !isNotLoggedIn();
	}

	/**
	 * Returns true if user with given username is logged in. Otherwise it returns false.
	 * @param username
	 * @return
	 */
	public boolean isUserLoggedIn(String username) {
		if(sessionRegistry == null) {
			throw new IllegalStateException("SessionRegistry is null. It must be set to use this method");
		}
		List principals = Arrays.asList(sessionRegistry.getAllPrincipals());
		for(Object p : principals) {
			if(username.equals(p)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Parse security attributes string like roles. E.g. "ROLE_USER,ROLE_ADMIN,ROLE_SUPERVISOR".
	 * Returns array of this attributes.
	 * @param configAttributeDefinitionString attributes array
	 * @return
	 */
	private Set<ConfigAttribute> parseConfigAttributeDefinition(String configAttributeDefinitionString) {
		final Set configAttributesDefinitions = new HashSet();
		final StringTokenizer tokenizer;
		tokenizer = new StringTokenizer(configAttributeDefinitionString, ",", false);

		while (tokenizer.hasMoreTokens()) {
			ConfigAttribute config = (ConfigAttribute)(tokenizer.nextToken().trim());
			configAttributesDefinitions.add(config);
		}

//		return (String[]) configAttributesDefinitions.toArray(new String[configAttributesDefinitions.size()]);
		return configAttributesDefinitions;
	}

	/**
	 * This method may secure object. It used accessDecisionManager do make decision.
	 * @param object Resource we want to secure. It can be model object or something else Spring Security ACL can recognize
	 * @param configAttributeDefinitionString config attributes used to activate concrete voters
	 * @return access denied or not
	 */
	public boolean secure(Object object, String configAttributeDefinitionString, AccessDecisionManager accessDecisionManager) {
		//new SpringSecurityFaceletsFunctions();
		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			return false;
		}
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        ConfigAttributeDefinition config = new ConfigAttributeDefinition(parseConfigAttributeDefinition(configAttributeDefinitionString));
		  try {
			accessDecisionManager.decide(authentication, object, SecurityConfig.createSingleAttributeList(configAttributeDefinitionString));
		}catch(AccessDeniedException e) {
			//e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * This method don't get object to secure. It means decision is made in different way (for example user roles).
	 * @param configAttributeDefinitionString
	 * @param accessDecisionManager
	 * @return
	 */
	public boolean secure(String configAttributeDefinitionString, AccessDecisionManager accessDecisionManager) {
		return secure(null, configAttributeDefinitionString, accessDecisionManager);
	}

	public List getVoters() {
		return voters;
	}

	public void setVoters(List voters) {
		this.voters = voters;
	}

	public void setSessionRegistry(SessionRegistry sessionRegistry) {
		this.sessionRegistry = sessionRegistry;
	}
}
