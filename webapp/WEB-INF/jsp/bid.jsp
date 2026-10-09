<% String pageTitle = "Review Bid"; %>
<%@ include file="header.jsp" %>
<%
    Item it     = (Item) request.getAttribute("item");
    User seller = (User) request.getAttribute("seller");
    String amount = (String) request.getAttribute("amount");
    String error  = (String) request.getAttribute("error");
    if (amount == null) amount = "";
%>

<FONT SIZE="4"><B>Review and confirm your bid</B></FONT><BR><BR>

<% if (error != null) { %>
  <TABLE WIDTH="100%" CELLPADDING="6" CELLSPACING="0" BORDER="0" CLASS="err">
  <TR><TD><FONT SIZE="2"><B>Your bid was not placed.</B><BR>
  <ebay:esc value="<%= error %>"/></FONT></TD></TR></TABLE><BR>
<% } %>

<TABLE WIDTH="100%" CELLPADDING="4" CELLSPACING="1" BORDER="0" BGCOLOR="#CCCCCC">
<TR CLASS="hdr"><TD COLSPAN="2">Item you are bidding on</TD></TR>
<TR BGCOLOR="#FFFFFF"><TD WIDTH="140"><FONT SIZE="2">Item</FONT></TD>
    <TD><FONT SIZE="2"><A HREF="<%= cp %>/item?id=<%= it.getId() %>"><ebay:esc value="<%= it.getTitle() %>"/></A>
    <FONT SIZE="1" COLOR="#666666">(# <%= it.getId() %>)</FONT></FONT></TD></TR>
<TR BGCOLOR="#FFFFFF"><TD><FONT SIZE="2">Seller</FONT></TD>
    <TD><FONT SIZE="2"><ebay:feedback user="<%= seller %>"/></FONT></TD></TR>
<TR BGCOLOR="#FFFFFF"><TD><FONT SIZE="2">Current bid</FONT></TD>
    <TD><FONT SIZE="2"><B><ebay:money cents="<%= it.getCurrentCents() %>"/></B></FONT></TD></TR>
<TR BGCOLOR="#FFFFFF"><TD><FONT SIZE="2">Your minimum bid</FONT></TD>
    <TD><FONT SIZE="2"><B><ebay:money cents="<%= it.getMinimumBidCents() %>"/></B></FONT></TD></TR>
<TR BGCOLOR="#FFFFFF"><TD><FONT SIZE="2">Time left</FONT></TD>
    <TD><FONT SIZE="2"><ebay:timeLeft end="<%= it.getEndsAt() %>"/></FONT></TD></TR>
</TABLE>

<BR>

<FORM METHOD="POST" ACTION="<%= cp %>/bid">
<INPUT TYPE="HIDDEN" NAME="id" VALUE="<%= it.getId() %>">
<INPUT TYPE="HIDDEN" NAME="confirm" VALUE="1">
<TABLE WIDTH="100%" CELLPADDING="6" CELLSPACING="1" BORDER="0" BGCOLOR="#CCCCCC">
<TR CLASS="hdr"><TD>Your maximum bid</TD></TR>
<TR BGCOLOR="#FFFFFF"><TD>
  <FONT SIZE="2">
  US $ <INPUT TYPE="TEXT" NAME="amount" SIZE="12"
              VALUE="<ebay:esc value="<%= amount %>"/>">
  &nbsp;<INPUT TYPE="SUBMIT" VALUE="Place Bid">
  <BR><BR>
  <FONT SIZE="1">
  eBay bids for you, raising your bid by the smallest amount needed to keep
  you in the lead, up to your maximum. Your maximum is never shown to the
  seller or to other bidders while the auction is running.
  <BR><BR>
  <B>You are agreeing to a contract.</B> If you are the winning bidder you
  will enter into a legally binding contract to purchase the item from the
  seller. You are responsible for reading the full item listing and the
  seller's terms.
  </FONT>
  </FONT>
</TD></TR>
</TABLE>
</FORM>

<FONT SIZE="2"><A HREF="<%= cp %>/item?id=<%= it.getId() %>">&lt; Back to the item</A></FONT>

<%@ include file="footer.jsp" %>
