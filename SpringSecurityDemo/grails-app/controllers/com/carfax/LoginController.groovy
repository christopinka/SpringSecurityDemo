package com.carfax

import org.springframework.security.authentication.AuthenticationTrustResolverImpl
import org.springframework.security.authentication.DisabledException

class LoginController {
	// utility security methods (like isLoggedIn)
	def securityService;
	private final authenticationTrustResolver = new AuthenticationTrustResolverImpl()

	def index = {
		if (isLoggedIn()) {
			redirect uri: '/'
		}
		else {
			redirect action: auth, params: params
		}
	}

	/**
	 * Show the login page.
	 */
	def auth = {
		nocache response

		if (isLoggedIn()) {
			redirect uri: '/'
			return
		}

		String view
		String postUrl

		view = 'auth'
		postUrl = "${request.contextPath}/j_spring_security_check"

		render view: view, model: [postUrl: postUrl]
	}

	/**
	 * Show denied page.
	 */
	def denied = {
		if (isLoggedIn() && authenticationTrustResolver.isRememberMe(SCH.context?.authentication)) {
			// have cookie but the page is guarded with IS_AUTHENTICATED_FULLY
			redirect action: full, params: params
		}
	}

	/**
	 * Login page for users with a remember-me cookie but accessing a IS_AUTHENTICATED_FULLY page.
	 */
	def full = {
		render view: 'auth', params: params,
				model: [hasCookie: authenticationTrustResolver.isRememberMe(SCH.context?.authentication)]
	}

	/**
	 * login failed
	 */
	def authfail = {

		def username = session[AuthenticationProcessingFilter.SPRING_SECURITY_LAST_USERNAME_KEY]
		def msg = ''
		def exception = session[AbstractProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY]
		if (exception) {
			if (exception instanceof DisabledException) {
				msg = "\"$username\" is disabled."
			}
			else {
				msg = "\"$username\" wrong username or password."
			}
		}

		flash.message = msg
		redirect action: auth, params: params
	}

	/**
	 * Check if logged in.
	 */
	private boolean isLoggedIn() {
		return securityService.isLoggedIn()
	}

	/** cache controls */
	private void nocache(response) {
		response.setHeader('Cache-Control', 'no-cache') // HTTP 1.1
		response.addDateHeader('Expires', 0)
		response.setDateHeader('max-age', 0)
		response.setIntHeader ('Expires', -1) //prevents caching at the proxy server
		response.addHeader('cache-Control', 'private') //IE5.x only
	}
}