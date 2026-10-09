<% String pageTitle = "Home"; %>
<%@ include file="header.jsp" %>
<%
    List cats = (List) request.getAttribute("categories");
    AuctionHouse shop = (AuctionHouse) application.getAttribute("ebay.house");
%>

<TABLE WIDTH="100%" CELLPADDING="6" CELLSPACING="0" BORDER="0" CLASS="note">
<TR><TD>
  <FONT SIZE="2"><B>Welcome to eBay!</B> &nbsp;
  <%= request.getAttribute("openCount") %> items are up for auction right now,
  listed by <%= request.getAttribute("memberCount") %> members.
  <ebay:anonymous>
    &nbsp;<A HREF="<%= cp %>/login"><B>Register free</B></A> to bid.
  </ebay:anonymous>
  </FONT>
</TD></TR>
</TABLE>
<BR>

<TABLE WIDTH="100%" CELLPADDING="0" CELLSPACING="0" BORDER="0">
<TR VALIGN="TOP">

  <TD WIDTH="180">
    <TABLE WIDTH="100%" CELLPADDING="4" CELLSPACING="1" BORDER="0" BGCOLOR="#CCCCCC">
    <TR CLASS="hdr"><TD>Categories</TD></TR>
    <TR BGCOLOR="#FFFFFF"><TD>
    <FONT SIZE="2">
    <%
        for (int i = 0; i < cats.size(); i++) {
            Category c = (Category) cats.get(i);
            List kids = shop.getChildren(c.getId());
    %>
      <B><A HREF="<%= cp %>/browse?cat=<%= c.getId() %>"><ebay:esc value="<%= c.getName() %>"/></A></B>
      <FONT SIZE="1" COLOR="#666666">(<%= shop.countOpenIn(c.getId()) %>)</FONT><BR>
      <FONT SIZE="1">
      <%
          for (int k = 0; k < kids.size(); k++) {
              Category kid = (Category) kids.get(k);
      %>
        &nbsp;&nbsp;<A HREF="<%= cp %>/browse?cat=<%= kid.getId() %>"><ebay:esc value="<%= kid.getName() %>"/></A><BR>
      <% } %>
      </FONT>
    <% } %>
    </FONT>
    </TD></TR>
    </TABLE>
  </TD>

  <TD WIDTH="12"></TD>

  <TD>
    <TABLE WIDTH="100%" CELLPADDING="0" CELLSPACING="0" BORDER="0">
    <TR><TD>
      <FONT SIZE="3"><B>Ending Soon</B></FONT>
      <FONT SIZE="1">&#151; going, going...</FONT>
      <BR>
      <jsp:include page="itemrows.jsp">
        <jsp:param name="list" value="ending"/>
        <jsp:param name="showSeller" value="1"/>
      </jsp:include>
      <BR>
      <FONT SIZE="3"><B>Hot Items</B></FONT>
      <FONT SIZE="1">&#151; most bids</FONT>
      <BR>
      <jsp:include page="itemrows.jsp">
        <jsp:param name="list" value="hot"/>
        <jsp:param name="showSeller" value="1"/>
      </jsp:include>
    </TD></TR>
    </TABLE>
  </TD>

</TR>
</TABLE>

<%@ include file="footer.jsp" %>
