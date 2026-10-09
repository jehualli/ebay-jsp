<% String pageTitle = "Feedback Profile"; %>
<%@ include file="header.jsp" %>
<%
    User member = (User) request.getAttribute("member");
    List comments = (List) request.getAttribute("comments");
    String error = (String) request.getAttribute("error");
    AuctionHouse shop = (AuctionHouse) application.getAttribute("ebay.house");
%>

<FONT SIZE="4"><B>Feedback Profile</B></FONT><BR><BR>

<% if ("1".equals(request.getParameter("left"))) { %>
  <TABLE WIDTH="100%" CELLPADDING="6" CELLSPACING="0" BORDER="0" CLASS="note">
  <TR><TD><FONT SIZE="2">Thank you. Your comment has been added to this
  member's profile, permanently.</FONT></TD></TR></TABLE><BR>
<% } %>

<% if (error != null) { %>
  <TABLE WIDTH="100%" CELLPADDING="6" CELLSPACING="0" BORDER="0" CLASS="err">
  <TR><TD><FONT SIZE="2"><ebay:esc value="<%= error %>"/></FONT></TD></TR></TABLE><BR>
<% } %>

<TABLE WIDTH="100%" CELLPADDING="6" CELLSPACING="1" BORDER="0" BGCOLOR="#CCCCCC">
<TR BGCOLOR="#FFFFFF"><TD>
  <FONT SIZE="3"><B><ebay:feedback user="<%= member %>" link="<%= false %>"/></B></FONT><BR>
  <FONT SIZE="1">
  <ebay:esc value="<%= member.getLocation() %>"/> &#149;
  member since <%= Text.stamp(member.getJoined()).substring(0, 9) %>
  </FONT>
</TD>
<TD ALIGN="RIGHT">
  <FONT SIZE="2">
  <B><%= member.getPositivePercent() %></B> positive<BR>
  <FONT SIZE="1" COLOR="#008000"><%= member.getPositives() %> praise</FONT> &#149;
  <FONT SIZE="1" COLOR="#666666"><%= member.getNeutrals() %> neutral</FONT> &#149;
  <FONT SIZE="1" COLOR="#CC0000"><%= member.getNegatives() %> complaints</FONT>
  </FONT>
</TD></TR>
</TABLE>

<BR>

<TABLE WIDTH="100%" CELLPADDING="4" CELLSPACING="1" BORDER="0" BGCOLOR="#CCCCCC">
<TR CLASS="hdr"><TD WIDTH="70">Rating</TD><TD WIDTH="150">From</TD><TD>Comment</TD></TR>
<%
    if (comments.isEmpty()) {
%>
  <TR BGCOLOR="#FFFFFF"><TD COLSPAN="3"><FONT SIZE="2"><I>This member has no
  feedback comments yet.</I></FONT></TD></TR>
<%
    }
    for (int i = 0; i < comments.size(); i++) {
        Feedback f = (Feedback) comments.get(i);
        String colour = f.getRating() > 0 ? "#008000"
                      : (f.getRating() < 0 ? "#CC0000" : "#666666");
        String mark = f.getRating() > 0 ? "+" : (f.getRating() < 0 ? "-" : "0");
%>
<TR BGCOLOR="<%= i % 2 == 0 ? "#FFFFFF" : "#F4F4F4" %>" VALIGN="TOP">
  <TD NOWRAP><FONT SIZE="2" COLOR="<%= colour %>"><B><%= mark %></B>
    <FONT SIZE="1"><%= f.getRatingLabel() %></FONT></FONT></TD>
  <TD NOWRAP><FONT SIZE="1"><ebay:feedback user="<%= shop.getUser(f.getFrom()) %>"/></FONT></TD>
  <TD><FONT SIZE="2"><ebay:esc value="<%= f.getComment() %>"/></FONT>
      <BR><FONT SIZE="1" COLOR="#999999"><%= Text.stamp(f.getTime()) %></FONT></TD>
</TR>
<% } %>
</TABLE>

<BR>

<ebay:loggedIn>
<% if (!member.getNick().equalsIgnoreCase(meNick)) { %>
  <FORM METHOD="POST" ACTION="<%= cp %>/feedback">
  <INPUT TYPE="HIDDEN" NAME="user" VALUE="<ebay:esc value="<%= member.getNick() %>"/>">
  <TABLE WIDTH="100%" CELLPADDING="6" CELLSPACING="1" BORDER="0" BGCOLOR="#CCCCCC">
  <TR CLASS="hdr"><TD>Leave feedback for <ebay:esc value="<%= member.getNick() %>"/></TD></TR>
  <TR BGCOLOR="#FFFFFF"><TD>
    <FONT SIZE="2">
    <INPUT TYPE="RADIO" NAME="rating" VALUE="1" CHECKED> Praise
    <INPUT TYPE="RADIO" NAME="rating" VALUE="0"> Neutral
    <INPUT TYPE="RADIO" NAME="rating" VALUE="-1"> Complaint
    <BR>
    <INPUT TYPE="TEXT" NAME="comment" SIZE="60" MAXLENGTH="80">
    <INPUT TYPE="SUBMIT" VALUE="Leave Feedback">
    <BR><FONT SIZE="1">80 characters. Feedback cannot be withdrawn, so think
    before you type.</FONT>
    </FONT>
  </TD></TR>
  </TABLE>
  </FORM>
<% } %>
</ebay:loggedIn>

<ebay:anonymous>
  <FONT SIZE="2"><A HREF="<%= cp %>/login">Sign in</A> to leave feedback for
  this member.</FONT>
</ebay:anonymous>

<%@ include file="footer.jsp" %>
