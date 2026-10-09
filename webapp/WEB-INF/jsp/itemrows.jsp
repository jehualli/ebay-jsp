<%--
  A reusable listing table, pulled in with <jsp:include> -- a *runtime*
  include, so this file compiles to its own servlet and shares nothing with
  its caller except request scope. Which list to draw arrives as a
  <jsp:param>, read back here with request.getParameter().
--%>
<%@ page import="mx.jehualli.ebay.service.*" %>
<%@ page import="mx.jehualli.ebay.util.*" %>
<%@ page import="mx.jehualli.ebay.model.*" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://ebay.example.com/taglib" prefix="ebay" %>
<%
    String base = request.getContextPath();
    String listName = request.getParameter("list");
    if (listName == null) listName = "items";
    List rows = (List) request.getAttribute(listName);
    if (rows == null) rows = new ArrayList();
    boolean showSeller = "1".equals(request.getParameter("showSeller"));
    AuctionHouse shop = (AuctionHouse) application.getAttribute("ebay.house");
%>
<TABLE WIDTH="100%" CELLPADDING="3" CELLSPACING="1" BORDER="0" BGCOLOR="#CCCCCC">
<TR CLASS="hdr">
  <TD>Item</TD>
  <TD ALIGN="RIGHT" NOWRAP>Price</TD>
  <TD ALIGN="RIGHT" NOWRAP>Bids</TD>
  <% if (showSeller) { %><TD NOWRAP>Seller</TD><% } %>
  <TD NOWRAP>Ends</TD>
</TR>
<%
    if (rows.isEmpty()) {
%>
<TR BGCOLOR="#FFFFFF"><TD COLSPAN="<%= showSeller ? 5 : 4 %>">
  <FONT SIZE="2"><I>No items found.</I></FONT>
</TD></TR>
<%
    }
    for (int i = 0; i < rows.size(); i++) {
        Item it = (Item) rows.get(i);
        String bg = (i % 2 == 0) ? "#FFFFFF" : "#F4F4F4";
%>
<TR BGCOLOR="<%= bg %>" VALIGN="TOP">
  <TD>
    <A HREF="<%= base %>/item?id=<%= it.getId() %>"><ebay:esc value="<%= it.getTitle() %>"/></A>
    <% if (it.hasReserve() && !it.isReserveMet()) { %>
      <FONT SIZE="1" COLOR="#CC6600">(reserve not met)</FONT>
    <% } %>
  </TD>
  <TD ALIGN="RIGHT" NOWRAP>
    <% if (it.getBidCount() == 0) { %>
      <FONT COLOR="#666666"><ebay:money cents="<%= it.getCurrentCents() %>"/></FONT>
    <% } else { %>
      <B><ebay:money cents="<%= it.getCurrentCents() %>"/></B>
    <% } %>
  </TD>
  <TD ALIGN="RIGHT"><%= it.getBidCount() == 0 ? "-" : String.valueOf(it.getBidCount()) %></TD>
  <% if (showSeller) { %>
    <TD NOWRAP><FONT SIZE="1"><ebay:feedback user="<%= shop.getUser(it.getSeller()) %>"/></FONT></TD>
  <% } %>
  <TD NOWRAP><FONT SIZE="1"><ebay:timeLeft end="<%= it.getEndsAt() %>"/></FONT></TD>
</TR>
<%
    }
%>
</TABLE>
