/*
 * Copyright (c) 2021, 2022 Contributors to the Eclipse Foundation
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * Contributors:
 *   2021 : Payara Foundation and/or its affiliates
 */
package testeAuth;

import java.io.IOException;

import jakarta.annotation.security.DeclareRoles;
//import jakarta.security.enterprise.authentication.mechanism.http.OpenIdAuthenticationMechanismDefinition;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HttpConstraint;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Gaurav Gupta
 * @author Rudy De Busscher
 */
@WebServlet("/Secured")
//@OpenIdAuthenticationMechanismDefinition(
//    providerURI = "http://localhost:8080/auth/auth/realms/authTest",
//    clientId = "authTest",
//    clientSecret = "3fd70ff4-fe2b-47b6-a8a3-cd1cf281a937",
//    redirectToOriginalResource = true,
//        scope = "openid",
//        useNonce = false
//)
@DeclareRoles("MANAGER")
@ServletSecurity(@HttpConstraint(rolesAllowed = "MANAGER"))
public class SecuredServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println("This is a secured web page");
    }
}
