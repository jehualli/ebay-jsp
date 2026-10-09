<%--
  The <error-page> target. isErrorPage="true" is what gives a JSP the
  implicit "exception" object; the container also passes the details as
  javax.servlet.error.* request attributes.
--%>
<%@ page isErrorPage="true" %>
<% String pageTitle = "Error"; %>
<%@ include file="header.jsp" %>

<TABLE WIDTH="100%" CELLPADDING="8" CELLSPACING="0" BORDER="0" CLASS="err">
<TR><TD>
  <FONT SIZE="4"><B>We're sorry...</B></FONT><BR><BR>
  <FONT SIZE="2">
  The page you requested could not be displayed.
  <BR><BR>
  <%
      Object code = request.getAttribute("javax.servlet.error.status_code");
      Object uri  = request.getAttribute("javax.servlet.error.request_uri");
      Object msg  = request.getAttribute("javax.servlet.error.message");
      Object type = request.getAttribute("javax.servlet.error.exception_type");
  %>
  <% if (code != null) { %>Error code: <TT><%= code %></TT><BR><% } %>
  <% if (uri  != null) { %>Requested: <TT><%= Text.esc(String.valueOf(uri)) %></TT><BR><% } %>
  <% if (msg  != null) { %>Message: <TT><%= Text.esc(String.valueOf(msg)) %></TT><BR><% } %>
  <% if (type != null) { %>Exception: <TT><%= Text.esc(String.valueOf(type)) %></TT><BR><% } %>
  <% if (exception != null) { %>Detail: <TT><%= Text.esc(exception.toString()) %></TT><BR><% } %>
  <BR>
  <A HREF="<%= cp %>/">Return to the eBay home page</A>
  </FONT>
</TD></TR>
</TABLE>

<%@ include file="footer.jsp" %>
