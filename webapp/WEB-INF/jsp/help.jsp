<% String pageTitle = "Help"; %>
<%@ include file="header.jsp" %>

<FONT SIZE="4"><B>Help &#151; Frequently Asked Questions</B></FONT><BR><BR>

<TABLE WIDTH="100%" CELLPADDING="6" CELLSPACING="1" BORDER="0" BGCOLOR="#CCCCCC">

<TR CLASS="hdr"><TD>How does bidding work?</TD></TR>
<TR BGCOLOR="#FFFFFF"><TD><FONT SIZE="2">
You enter the most you are willing to pay &#151; your <B>maximum</B>. eBay
then bids for you, raising your bid by the smallest increment needed to keep
you in the lead, and stops at your maximum. Everyone else sees only the
current price, never your maximum. This is why the price often jumps the
instant a new bidder arrives: their bid runs straight into a standing proxy.
</FONT></TD></TR>

<TR CLASS="hdr"><TD>What are the bid increments?</TD></TR>
<TR BGCOLOR="#FFFFFF"><TD><FONT SIZE="2">
<TABLE CELLPADDING="2" CELLSPACING="0" BORDER="0"><FONT SIZE="1">
<TR><TD>Under $1.00</TD><TD>$0.05</TD><TD WIDTH="20"></TD><TD>$100.00 - $249.99</TD><TD>$2.50</TD></TR>
<TR><TD>$1.00 - $4.99</TD><TD>$0.25</TD><TD></TD><TD>$250.00 - $499.99</TD><TD>$5.00</TD></TR>
<TR><TD>$5.00 - $24.99</TD><TD>$0.50</TD><TD></TD><TD>$500.00 - $999.99</TD><TD>$10.00</TD></TR>
<TR><TD>$25.00 - $99.99</TD><TD>$1.00</TD><TD></TD><TD>$1,000.00 and up</TD><TD>$25.00+</TD></TR>
</FONT></TABLE>
</FONT></TD></TR>

<TR CLASS="hdr"><TD>What is a reserve price?</TD></TR>
<TR BGCOLOR="#FFFFFF"><TD><FONT SIZE="2">
A secret minimum the seller will accept. Bidding can start below it, but if
the auction ends under the reserve the item does not sell. Listings show
<I>reserve not met</I> until the price clears it.
</FONT></TD></TR>

<TR CLASS="hdr"><TD>What do the coloured stars mean?</TD></TR>
<TR BGCOLOR="#FFFFFF"><TD><FONT SIZE="2">
Feedback rating. Members earn +1 for praise and -1 for a complaint, and the
star colour marks the tier:
<FONT SIZE="1">
<FONT COLOR="#FFD700"><B>&#9733;</B></FONT> 10+ &#149;
<FONT COLOR="#40A0FF"><B>&#9733;</B></FONT> 50+ &#149;
<FONT COLOR="#40E0D0"><B>&#9733;</B></FONT> 100+ &#149;
<FONT COLOR="#A020F0"><B>&#9733;</B></FONT> 500+ &#149;
<FONT COLOR="#FF0000"><B>&#9733;</B></FONT> 1,000+ &#149;
<FONT COLOR="#00A000"><B>&#9733;</B></FONT> 5,000+ &#149;
<FONT COLOR="#FF00FF"><B>&#9733;</B></FONT> 10,000+
</FONT>
</FONT></TD></TR>

<TR CLASS="hdr"><TD>Is this the real eBay?</TD></TR>
<TR BGCOLOR="#FFFFFF"><TD><FONT SIZE="2">
No. This is a reconstruction of a 1999-era auction site, built to demonstrate
how web applications were written with Java servlets and JSP at the time:
servlet controllers, JavaBeans, JSP 1.2 views and a custom tag library. No
item is real, no money changes hands, and the marketplace resets every time
the server restarts.
</FONT></TD></TR>

</TABLE>

<%@ include file="footer.jsp" %>
