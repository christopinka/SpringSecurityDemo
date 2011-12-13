

<%@ page import="com.carfax.Vehicle" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'vehicle.label', default: 'Vehicle')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${vehicleInstance}">
            <div class="errors">
                <g:renderErrors bean="${vehicleInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${vehicleInstance?.id}" />
                <g:hiddenField name="version" value="${vehicleInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="vin"><g:message code="vehicle.vin.label" default="Vin" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: vehicleInstance, field: 'vin', 'errors')}">
                                    <g:textField name="vin" maxlength="17" value="${vehicleInstance?.vin}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="lastOdoDate"><g:message code="vehicle.lastOdoDate.label" default="Last Odo Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: vehicleInstance, field: 'lastOdoDate', 'errors')}">
                                    <g:datePicker name="lastOdoDate" precision="day" value="${vehicleInstance?.lastOdoDate}" default="none" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="lastOdoSource"><g:message code="vehicle.lastOdoSource.label" default="Last Odo Source" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: vehicleInstance, field: 'lastOdoSource', 'errors')}">
                                    <g:textField name="lastOdoSource" value="${vehicleInstance?.lastOdoSource}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="make"><g:message code="vehicle.make.label" default="Make" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: vehicleInstance, field: 'make', 'errors')}">
                                    <g:textField name="make" value="${vehicleInstance?.make}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="model"><g:message code="vehicle.model.label" default="Model" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: vehicleInstance, field: 'model', 'errors')}">
                                    <g:textField name="model" value="${vehicleInstance?.model}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="year"><g:message code="vehicle.year.label" default="Year" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: vehicleInstance, field: 'year', 'errors')}">
                                    <g:textField name="year" value="${vehicleInstance?.year}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="account"><g:message code="vehicle.account.label" default="Account" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: vehicleInstance, field: 'account', 'errors')}">
                                    <g:select name="account.id" from="${com.carfax.Account.list()}" optionKey="id" value="${vehicleInstance?.account?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="historyRecords"><g:message code="vehicle.historyRecords.label" default="History Records" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: vehicleInstance, field: 'historyRecords', 'errors')}">
                                    
<ul>
<g:each in="${vehicleInstance?.historyRecords?}" var="h">
    <li><g:link controller="historyRecord" action="show" id="${h.id}">${h?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="historyRecord" action="create" params="['vehicle.id': vehicleInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'historyRecord.label', default: 'HistoryRecord')])}</g:link>

                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="lastOdoMileage"><g:message code="vehicle.lastOdoMileage.label" default="Last Odo Mileage" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: vehicleInstance, field: 'lastOdoMileage', 'errors')}">
                                    <g:textField name="lastOdoMileage" value="${fieldValue(bean: vehicleInstance, field: 'lastOdoMileage')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="numberOfRecallRecords"><g:message code="vehicle.numberOfRecallRecords.label" default="Number Of Recall Records" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: vehicleInstance, field: 'numberOfRecallRecords', 'errors')}">
                                    <g:textField name="numberOfRecallRecords" value="${fieldValue(bean: vehicleInstance, field: 'numberOfRecallRecords')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="numberOfServiceRecords"><g:message code="vehicle.numberOfServiceRecords.label" default="Number Of Service Records" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: vehicleInstance, field: 'numberOfServiceRecords', 'errors')}">
                                    <g:textField name="numberOfServiceRecords" value="${fieldValue(bean: vehicleInstance, field: 'numberOfServiceRecords')}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
