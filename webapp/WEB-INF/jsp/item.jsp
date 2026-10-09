<% String pageTitle = "Item"; %>
<%@ include file="header.jsp" %>
<%
    Item it        = (Item) request.getAttribute("item");
    User seller    = (User) request.getAttribute("seller");
    List bids      = (List) request.getAttribute("bids");
    List path      = (List) request.getAttribute("path");
    boolean onList = ((Boolean) request.getAttribute("watching")).booleanValue();
    AuctionHouse shop = (AuctionHouse) application.getAttribute("ebay.house");
    boolean mine   = meNick != null && meNick.equalsIgnoreCase(it.getSeller());
%>

<FONT SIZE="1">
<A HREF="<%= cp %>/browse">All Categories</A>
<% for (int i = 0; i < path.size(); i++) {
       Category c = (Category) path.get(i); %>
  &gt; <A HREF="<%= cp %>/browse?cat=<%= c.getId() %>"><ebay:esc value="<%= c.getName() %>"/></A>
<% } %>
</FONT>
<BR>

<% if ("1".equals(request.getParameter("listed"))) { %>
  <TABLE WIDTH="100%" CELLPADDING="6" CELLSPACING="0" BORDER="0" CLASS="note">
  <TR><TD><FONT SIZE="2"><B>Congratulations!</B> Your item has been listed.
  Bookmark this page &#151; this is item number <%= it.getId() %>.</FONT></TD></TR>
  </TABLE><BR>
<% } %>

<% if (it.isEnded()) { %>
  <TABLE WIDTH="100%" CELLPADDING="6" CELLSPACING="0" BORDER="0" CLASS="note">
  <TR><TD><FONT SIZE="2">
  <B>This auction has ended.</B>
  <% if (it.getWinner() != null) { %>
    Winning bidder: <B><ebay:esc value="<%= it.getWinner() %>"/></B> at
    <B><ebay:money cents="<%= it.getCurrentCents() %>"/></B>.
    Seller and buyer should contact each other within three days.
  <% } else if (it.hasReserve() && it.getBidCount() > 0) { %>
    The reserve price was not met, so the item did not sell.
  <% } else { %>
    No bids were received.
  <% } %>
  </FONT></TD></TR></TABLE><BR>
<% } %>

<FONT SIZE="4"><B><ebay:esc value="<%= it.getTitle() %>"/></B></FONT><BR>
<FONT SIZE="1" COLOR="#666666">Item # <%= it.getId() %></FONT>
<BR><BR>

<TABLE WIDTH="100%" CELLPADDING="0" CELLSPACING="0" BORDER="0"><TR VALIGN="TOP">

<TD WIDTH="200">
  <TABLE WIDTH="100%" HEIGHT="150" CELLPADDING="4" CELLSPACING="0" BORDER="1"
         BGCOLOR="#F4F4F4"><TR><TD ALIGN="CENTER">
    <FONT SIZE="1" COLOR="#999999">[ seller has not added<BR>a picture to this item ]</FONT>
  </TD></TR></TABLE>
  <BR>
  <TABLE WIDTH="100%" CELLPADDING="4" CELLSPACING="1" BORDER="0" BGCOLOR="#CCCCCC">
  <TR CLASS="hdr"><TD>Seller information</TD></TR>
  <TR BGCOLOR="#FFFFFF"><TD><FONT SIZE="2">
    <ebay:feedback user="<%= seller %>"/><BR>
    <FONT SIZE="1">
    <%= seller.getPositivePercent() %> positive feedback<BR>
    Member since <%= Text.stamp(seller.getJoined()).substring(0, 9) %><BR>
    <ebay:esc value="<%= seller.getLocation() %>"/><BR><BR>
    <A HREF="<%= cp %>/feedback?user=<%= seller.getNick() %>">Read feedback comments</A><BR>
    <A HREF="<%= cp %>/browse?seller=<%= seller.getNick() %>">View seller's other items</A>
    </FONT>
  </FONT></TD></TR>
  </TABLE>
</TD>

<TD WIDTH="12"></TD>

<TD>
  <TABLE WIDTH="100%" CELLPADDING="4" CELLSPACING="1" BORDER="0" BGCOLOR="#CCCCCC">
  <TR BGCOLOR="#FFFFFF"><TD WIDTH="130"><FONT SIZE="2">Currently</FONT></TD>
      <TD><FONT SIZE="3"><B><ebay:money cents="<%= it.getCurrentCents() %>"/></B></FONT>
      <% if (it.hasReserve()) { %>
        <FONT SIZE="1" COLOR="<%= it.isReserveMet() ? "#008000" : "#CC6600" %>">
        (<%= it.isReserveMet() ? "reserve met" : "reserve not met" %>)</FONT>
      <% } %>
      </TD></TR>
  <TR BGCOLOR="#FFFFFF"><TD><FONT SIZE="2">Quantity</FONT></TD>
      <TD><FONT SIZE="2">1</FONT></TD></TR>
  <TR BGCOLOR="#FFFFFF"><TD><FONT SIZE="2">Time left</FONT></TD>
      <TD><FONT SIZE="2"><B><ebay:timeLeft end="<%= it.getEndsAt() %>"/></B></FONT></TD></TR>
  <TR BGCOLOR="#FFFFFF"><TD><FONT SIZE="2"># of bids</FONT></TD>
      <TD><FONT SIZE="2"><%= it.getBidCount() %>
      <% if (it.getBidCount() > 0) { %>
        <FONT SIZE="1">(<A HREF="#history">bid history</A>)</FONT>
      <% } %>
      </FONT></TD></TR>
  <TR BGCOLOR="#FFFFFF"><TD><FONT SIZE="2">High bidder</FONT></TD>
      <TD><FONT SIZE="2">
      <% if (it.getLeader() == null) { %>
        <I>no bids yet</I>
      <% } else { %>
        <ebay:feedback user="<%= shop.getUser(it.getLeader()) %>"/>
      <% } %>
      </FONT></TD></TR>
  <TR BGCOLOR="#FFFFFF"><TD><FONT SIZE="2">Started at</FONT></TD>
      <TD><FONT SIZE="2"><ebay:money cents="<%= it.getStartCents() %>"/></FONT></TD></TR>
  <TR BGCOLOR="#FFFFFF"><TD><FONT SIZE="2">Location</FONT></TD>
      <TD><FONT SIZE="2"><ebay:esc value="<%= it.getLocation() %>"/></FONT></TD></TR>
  <TR BGCOLOR="#FFFFFF"><TD><FONT SIZE="2">Condition</FONT></TD>
      <TD><FONT SIZE="2"><ebay:esc value="<%= it.getCondition() %>"/></FONT></TD></TR>
  <TR BGCOLOR="#FFFFFF"><TD><FONT SIZE="2">Ends</FONT></TD>
      <TD><FONT SIZE="2"><%= Text.stamp(it.getEndsAt()) %></FONT></TD></TR>
  </TABLE>

  <BR>

  <% if (!it.isEnded()) { %>
    <TABLE WIDTH="100%" CELLPADDING="6" CELLSPACING="1" BORDER="0" BGCOLOR="#CCCCCC">
    <TR CLASS="hdr"><TD>Ready to bid?</TD></TR>
    <TR BGCOLOR="#FFFFFF"><TD>
      <% if (mine) { %>
        <FONT SIZE="2"><I>This is your own listing. Sellers may not bid on
        their own items.</I></FONT>
      <% } else { %>
        <FORM METHOD="GET" ACTION="<%= cp %>/bid">
        <INPUT TYPE="HIDDEN" NAME="id" VALUE="<%= it.getId() %>">
        <FONT SIZE="2">Your maximum bid:
        <INPUT TYPE="TEXT" NAME="amount" SIZE="10">
        <INPUT TYPE="SUBMIT" VALUE="Review Bid &gt;">
        <BR><FONT SIZE="1">
        Minimum bid is <B><ebay:money cents="<%= it.getMinimumBidCents() %>"/></B>.
        eBay will bid on your behalf, only as much as needed to keep you in
        the lead, up to your maximum.
        </FONT></FONT>
        </FORM>
      <% } %>
    </TD></TR>
    </TABLE>
    <BR>
    <ebay:loggedIn>
      <FONT SIZE="2">
      <A HREF="<%= cp %>/watch?id=<%= it.getId() %>&amp;back=/item?id=<%= it.getId() %>">
      <%= onList ? "Remove from my watch list" : "Add to my watch list" %></A>
      </FONT>
    </ebay:loggedIn>
  <% } %>
</TD>
</TR></TABLE>

<BR>
<TABLE WIDTH="100%" CELLPADDING="6" CELLSPACING="1" BORDER="0" BGCOLOR="#CCCCCC">
<TR CLASS="hdr"><TD>Description</TD></TR>
<TR BGCOLOR="#FFFFFF"><TD><FONT SIZE="2">
  <ebay:esc value="<%= it.getDescription() %>"/>
</FONT></TD></TR>
</TABLE>

<BR>
<A NAME="history"></A>
<TABLE WIDTH="100%" CELLPADDING="4" CELLSPACING="1" BORDER="0" BGCOLOR="#CCCCCC">
<TR CLASS="hdr"><TD COLSPAN="3">Bid History (<%= bids.size() %>)</TD></TR>
<% if (bids.isEmpty()) { %>
  <TR BGCOLOR="#FFFFFF"><TD COLSPAN="3"><FONT SIZE="2"><I>No bids have been
  placed on this item.</I></FONT></TD></TR>
<% } else { %>
  <TR CLASS="hdr"><TD>User ID</TD><TD ALIGN="RIGHT">Bid Amount</TD><TD>Date of Bid</TD></TR>
  <%
      for (int i = bids.size() - 1; i >= 0; i--) {
          Bid b = (Bid) bids.get(i);
  %>
  <TR BGCOLOR="<%= i % 2 == 0 ? "#FFFFFF" : "#F4F4F4" %>">
    <TD><FONT SIZE="2"><ebay:feedback user="<%= shop.getUser(b.getBidder()) %>"/></FONT></TD>
    <TD ALIGN="RIGHT"><FONT SIZE="2"><ebay:money cents="<%= b.getPriceCents() %>"/></FONT></TD>
    <TD><FONT SIZE="1"><%= Text.stamp(b.getTime()) %></FONT></TD>
  </TR>
  <% } %>
<% } %>
</TABLE>
<FONT SIZE="1" COLOR="#666666">Bid amounts shown are what each bid moved the
price to. Proxy maximums stay secret until the auction ends.</FONT>

<%@ include file="footer.jsp" %>
