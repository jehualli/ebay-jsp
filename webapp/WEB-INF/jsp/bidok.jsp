<% String pageTitle = "Bid Confirmed"; %>
<%@ include file="header.jsp" %>
<%
    BidResult r = (BidResult) request.getAttribute("result");
    Item it = r.getItem();
%>

<TABLE WIDTH="100%" CELLPADDING="8" CELLSPACING="0" BORDER="0"
       CLASS="<%= r.isLeading() ? "note" : "err" %>">
<TR><TD>
  <FONT SIZE="4"><B>
  <%= r.isLeading() ? "You are the current high bidder!" : "You have been outbid." %>
  </B></FONT><BR><BR>
  <FONT SIZE="2"><ebay:esc value="<%= r.getMessage() %>"/></FONT>
</TD></TR>
</TABLE>

<BR>

<TABLE WIDTH="100%" CELLPADDING="4" CELLSPACING="1" BORDER="0" BGCOLOR="#CCCCCC">
<TR BGCOLOR="#FFFFFF"><TD WIDTH="140"><FONT SIZE="2">Item</FONT></TD>
    <TD><FONT SIZE="2"><A HREF="<%= cp %>/item?id=<%= it.getId() %>"><ebay:esc value="<%= it.getTitle() %>"/></A></FONT></TD></TR>
<TR BGCOLOR="#FFFFFF"><TD><FONT SIZE="2">Current price</FONT></TD>
    <TD><FONT SIZE="2"><B><ebay:money cents="<%= it.getCurrentCents() %>"/></B></FONT></TD></TR>
<TR BGCOLOR="#FFFFFF"><TD><FONT SIZE="2">Time left</FONT></TD>
    <TD><FONT SIZE="2"><ebay:timeLeft end="<%= it.getEndsAt() %>"/></FONT></TD></TR>
</TABLE>

<BR>
<FONT SIZE="2">
<% if (!r.isLeading()) { %>
  <A HREF="<%= cp %>/bid?id=<%= it.getId() %>"><B>Bid again</B></A> &nbsp;|&nbsp;
<% } %>
<A HREF="<%= cp %>/item?id=<%= it.getId() %>">Back to the item</A> &nbsp;|&nbsp;
<A HREF="<%= cp %>/myebay?view=bidding">My eBay: items I'm bidding on</A>
</FONT>

<%@ include file="footer.jsp" %>
