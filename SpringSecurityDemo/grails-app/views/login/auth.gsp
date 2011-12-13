<head>
<title>Login</title>
</head>
<body>
    <form action='${postUrl}' method='POST' id='loginForm' class="big-form">
    <table>
        <tr>
            <td></td>
            <td>
                <g:if test='${flash.message}'>
                    <div class='error'>${flash.message}</div>
                </g:if>
            </td>
        </tr>
        <tr>
            <td>
                <label for='j_username'>Login ID</label>
            </td>
            <td>
                <input type='text' class='text_' name='j_username' id='j_username' value='${request.remoteUser}' />
            </td>
        </tr>
        <tr>
            <td>
                <label for='j_password'>Password</label>
            </td>
            <td>
                <input type='password' class='text_' name='j_password' id='j_password' />
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <input type='checkbox' class='chk' name='_spring_security_remember_me' id='remember_me'
                    <g:if test='${hasCookie}'>checked='checked'</g:if> 
                />
                <label for='remember_me'>Remember me</label>
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <input type='submit' value='Login' class="button" />
            </td>
        </tr>
    </table>
    </form>
</body>