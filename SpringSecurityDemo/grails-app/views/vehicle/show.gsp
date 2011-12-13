
<%@ page import="com.carfax.Vehicle" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'vehicle.label', default: 'Vehicle')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="vehicle.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: vehicleInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="vehicle.vin.label" default="Vin" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: vehicleInstance, field: "vin")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="vehicle.lastOdoDate.label" default="Last Odo Date" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${vehicleInstance?.lastOdoDate}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="vehicle.lastOdoSource.label" default="Last Odo Source" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: vehicleInstance, field: "lastOdoSource")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="vehicle.make.label" default="Make" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: vehicleInstance, field: "make")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="vehicle.model.label" default="Model" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: vehicleInstance, field: "model")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="vehicle.year.label" default="Year" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: vehicleInstance, field: "year")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="vehicle.account.label" default="Account" /></td>
                            
                            <td valign="top" class="value"><g:link controller="account" action="show" id="${vehicleInstance?.account?.id}">${vehicleInstance?.account?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="vehicle.historyRecords.label" default="History Records" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${vehicleInstance.historyRecords}" var="h">
                                    <li><g:link controller="historyRecord" action="show" id="${h.id}">${h?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="vehicle.lastOdoMileage.label" default="Last Odo Mileage" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: vehicleInstance, field: "lastOdoMileage")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="vehicle.numberOfRecallRecords.label" default="Number Of Recall Records" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: vehicleInstance, field: "numberOfRecallRecords")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="vehicle.numberOfServiceRecords.label" default="Number Of Service Records" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: vehicleInstance, field: "numberOfServiceRecords")}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${vehicleInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
