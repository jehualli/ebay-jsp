<% String pageTitle = "Browse"; %>
<%@ include file="header.jsp" %>
<%
    List path      = (List) request.getAttribute("path");
    List children  = (List) request.getAttribute("children");
    int  catId     = ((Integer) request.getAttribute("cat")).intValue();
    int  total     = ((Integer) request.getAttribute("total")).intValue();
    int  pageNo    = ((Integer) request.getAttribute("page")).intValue();
    int  pageCount = ((Integer) request.getAttribute("pages")).intValue();
    int  sortNo    = ((Integer) request.getAttribute("sort")).intValue();
    String query   = (String) request.getAttribute("q");
    String seller  = (String) request.getAttribute("seller");
    boolean done   = ((Boolean) request.getAttribute("completed")).booleanValue();
    boolean isSearch = ((Boolean) request.getAttribute("isSearch")).booleanValue();
    String pageBase = (String) request.getAttribute("pageBase");
%>

<FONT SIZE="1">
<A HREF="<%= cp %>/browse">All Categories</A>
<%
    for (int i = 0; i < path.size(); i++) {
        Category c = (Category) path.get(i);
%>
  &gt; <A HREF="<%= cp %>/browse?cat=<%= c.getId() %>"><ebay:esc value="<%= c.getName() %>"/></A>
<% } %>
</FONT>
<BR>

<FONT SIZE="4"><B>
<% if (seller != null && seller.length() > 0) { %>
  Items from <ebay:esc value="<%= seller %>"/>
<% } else if (isSearch && query.length() > 0) { %>
  Search results for "<ebay:esc value="<%= query %>"/>"
<% } else if (!path.isEmpty()) { %>
  <ebay:esc value="<%= ((Category) path.get(path.size() - 1)).getName() %>"/>
<% } else { %>
  All Categories
<% } %>
</B></FONT>
<FONT SIZE="2" COLOR="#666666">&nbsp;<%= total %> item<%= total == 1 ? "" : "s" %>
<%= done ? "completed" : "found" %></FONT>
<BR><BR>

<% if (!children.isEmpty()) { %>
  <TABLE WIDTH="100%" CELLPADDING="4" CELLSPACING="0" BORDER="0" CLASS="note">
  <TR><TD><FONT SIZE="2"><B>Sub-categories:</B>
  <%
      for (int i = 0; i < children.size(); i++) {
          Category c = (Category) children.get(i);
  %>
    <A HREF="<%= cp %>/browse?cat=<%= c.getId() %>"><ebay:esc value="<%= c.getName() %>"/></A><%= i < children.size() - 1 ? " &#149;" : "" %>
  <% } %>
  </FONT></TD></TR></TABLE><BR>
<% } %>

<FORM METHOD="GET" ACTION="<%= cp %>/search">
<TABLE WIDTH="100%" CELLPADDING="4" CELLSPACING="1" BORDER="0" BGCOLOR="#CCCCCC">
<TR CLASS="hdr"><TD>Refine your search</TD></TR>
<TR BGCOLOR="#FFFFFF"><TD>
  <FONT SIZE="2">
  Keywords <INPUT TYPE="TEXT" NAME="q" SIZE="16" VALUE="<ebay:esc value="<%= query %>"/>">
  &nbsp;Price from <INPUT TYPE="TEXT" NAME="min" SIZE="5">
  to <INPUT TYPE="TEXT" NAME="max" SIZE="5">
  &nbsp;Sort by
  <SELECT NAME="sort">
    <OPTION VALUE="0"<%= sortNo == 0 ? " SELECTED" : "" %>>Ending soonest</OPTION>
    <OPTION VALUE="1"<%= sortNo == 1 ? " SELECTED" : "" %>>Newly listed</OPTION>
    <OPTION VALUE="2"<%= sortNo == 2 ? " SELECTED" : "" %>>Price lowest</OPTION>
    <OPTION VALUE="3"<%= sortNo == 3 ? " SELECTED" : "" %>>Price highest</OPTION>
    <OPTION VALUE="4"<%= sortNo == 4 ? " SELECTED" : "" %>>Most bids</OPTION>
  </SELECT>
  &nbsp;<INPUT TYPE="CHECKBOX" NAME="completed" VALUE="1"<%= done ? " CHECKED" : "" %>>
  <FONT SIZE="1">completed items only</FONT>
  <INPUT TYPE="HIDDEN" NAME="cat" VALUE="<%= catId %>">
  <INPUT TYPE="SUBMIT" VALUE="Search">
  </FONT>
</TD></TR>
</TABLE>
</FORM>
<BR>

<jsp:include page="itemrows.jsp">
  <jsp:param name="list" value="items"/>
  <jsp:param name="showSeller" value="1"/>
</jsp:include>

<BR>
<TABLE WIDTH="100%" CELLPADDING="2" CELLSPACING="0" BORDER="0"><TR>
  <TD><FONT SIZE="1">Page <%= pageNo %> of <%= Math.max(1, pageCount) %></FONT></TD>
  <TD ALIGN="RIGHT">
    <ebay:pageLinks page="<%= pageNo %>" pages="<%= pageCount %>" base="<%= pageBase %>"/>
  </TD>
</TR></TABLE>

<%@ include file="footer.jsp" %>
