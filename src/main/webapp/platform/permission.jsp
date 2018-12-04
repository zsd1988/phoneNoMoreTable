
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if  test="${permissionBase.permissionName==selectName}">
    <ul style="display: block">
        <c:forEach var="permission"     items="${permissionBase.permissionsList}"   varStatus="status">
            <c:if test="${permission.permissionName==selectName}">
                <li class="has_sub ">
                <c:if test="${permission.className=='folder'}">
                    <a href="javascript:void(0)"    class="waves-effect subdrop folders_class"    canDel="${permission.canDelete}"   permissionId="${permission.id}"    permissionName="${permission.permissionName}" onclick="gotoPage('${permission.permissionUrl}','','${permission.folderType}')"><i class="${permission.className}"></i><span>${permission.permissionName}</span> </a>
                </c:if>
                <c:if test="${permission.className!='folder'}">
                    <a href="javascript:void(0)"    class="waves-effect subdrop "    canDel="${permission.canDelete}"   permissionId="${permission.id}"    permissionName="${permission.permissionName}" onclick="gotoPage('${permission.permissionUrl}','','${permission.folderType}')"><i class="${permission.className}"></i><span>${permission.permissionName}</span> </a>
                </c:if>
                <c:if test="${not empty permission.permissionsList}">
                    <c:set var="permissionBase"  value="${permission}"     scope="request" />
                    <c:import url="permission.jsp"/>
                </c:if>
                </li>
            </c:if>
            <c:if test="${permission.permissionName!=selectName}">
                <li class="has_sub ">
                    <c:if test="${permission.className=='folder'}">
                        <a href="javascript:void(0)"    class="waves-effect  folders_class"  canDel="${permission.canDelete}"   permissionId="${permission.id}"    permissionName="${permission.permissionName}"  onclick="gotoPage('${permission.permissionUrl}','','${permission.folderType}')"><i class="${permission.className}"></i><span>${permission.permissionName}</span> </a>
                    </c:if>
                    <c:if test="${permission.className!='folder'}">
                        <a href="javascript:void(0)"    class="waves-effect  "   canDel="${permission.canDelete}"   permissionId="${permission.id}"   permissionName="${permission.permissionName}"  onclick="gotoPage('${permission.permissionUrl}','','${permission.folderType}')"><i class="${permission.className}"></i><span>${permission.permissionName}</span> </a>
                    </c:if>
                <c:if test="${not empty permission.permissionsList}">
                    <c:set var="permissionBase"  value="${permission}"     scope="request" />
                    <c:import url="permission.jsp"/>
                </c:if>
                </li>
            </c:if>
        </c:forEach>
    </ul>
</c:if>
<c:if  test="${permissionBase.permissionName!=selectName}">
    <ul>
        <c:forEach var="permission"     items="${permissionBase.permissionsList}"   varStatus="status">
            <c:if test="${permission.permissionName==selectName}">
                <li class="has_sub ">
                    <c:if test="${permission.className=='folder'}">
                        <a href="javascript:void(0)"    class="waves-effect subdrop folders_class"  canDel="${permission.canDelete}"   permissionId="${permission.id}"    permissionName="${permission.permissionName}" onclick="gotoPage('${permission.permissionUrl}','','${permission.folderType}')"><i class="${permission.className}"></i><span>${permission.permissionName}</span> </a>
                    </c:if>
                    <c:if test="${permission.className!='folder'}">
                        <a href="javascript:void(0)"    class="waves-effect subdrop "   canDel="${permission.canDelete}"   permissionId="${permission.id}"    permissionName="${permission.permissionName}" onclick="gotoPage('${permission.permissionUrl}','','${permission.folderType}')"><i class="${permission.className}"></i><span>${permission.permissionName}</span> </a>
                    </c:if>
                <c:if test="${not empty permission.permissionsList}">
                    <c:set var="permissionBase"  value="${permission}"     scope="request" />
                    <c:import url="permission.jsp"/>
                </c:if>
                </li>
            </c:if>
            <c:if test="${permission.permissionName!=selectName}">
                <li class="has_sub ">
                    <c:if test="${permission.className=='folder'}">
                        <a href="javascript:void(0)"    class="waves-effect  folders_class"  canDel="${permission.canDelete}"   permissionId="${permission.id}"    permissionName="${permission.permissionName}"   onclick="gotoPage('${permission.permissionUrl}','','${permission.folderType}')"><i class="${permission.className}"></i><span>${permission.permissionName}</span> </a>
                    </c:if>
                    <c:if test="${permission.className!='folder'}">
                        <a href="javascript:void(0)"    class="waves-effect  "   canDel="${permission.canDelete}"   permissionId="${permission.id}"    permissionName="${permission.permissionName}"  onclick="gotoPage('${permission.permissionUrl}','','${permission.folderType}')"><i class="${permission.className}"></i><span>${permission.permissionName}</span> </a>
                    </c:if>
                <c:if test="${not empty permission.permissionsList}">
                    <c:set var="permissionBase"  value="${permission}"     scope="request" />
                    <c:import url="permission.jsp"/>
                </c:if>
                </li>
            </c:if>

        </c:forEach>
    </ul>
</c:if>