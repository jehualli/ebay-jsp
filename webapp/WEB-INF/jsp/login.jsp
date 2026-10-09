<% String pageTitle = "Sign In"; %>
<%@ include file="header.jsp" %>
<%
    String error = (String) request.getAttribute("error");
    String enteredNick = (String) request.getAttribute("nick");
    if (enteredNick == null) enteredNick = "";
%>

<FONT SIZE="4"><B>eBay Sign In</B></FONT><BR><BR>

<% if ("required".equals(request.getParameter("why"))) { %>
  <TABLE WIDTH="100%" CELLPADDING="6" CELLSPACING="0" BORDER="0" CLASS="note">
  <TR><TD><FONT SIZE="2">You must be a registered eBay member to do that.
  Sign in below, or register &#151; it's free.</FONT></TD></TR></TABLE><BR>
<% } %>

<% if (error != null) { %>
  <TABLE WIDTH="100%" CELLPADDING="6" CELLSPACING="0" BORDER="0" CLASS="err">
  <TR><TD><FONT SIZE="2"><B>Sorry:</B> <ebay:esc value="<%= error %>"/></FONT></TD></TR>
  </TABLE><BR>
<% } %>

<TABLE WIDTH="100%" CELLPADDING="0" CELLSPACING="0" BORDER="0"><TR VALIGN="TOP">

<TD WIDTH="50%">
  <TABLE WIDTH="100%" CELLPADDING="6" CELLSPACING="1" BORDER="0" BGCOLOR="#CCCCCC">
  <TR CLASS="hdr"><TD COLSPAN="2">Already a member?</TD></TR>
  <TR BGCOLOR="#FFFFFF"><TD COLSPAN="2">
    <FORM METHOD="POST" ACTION="<%= cp %>/login">
    <TABLE CELLPADDING="3" CELLSPACING="0" BORDER="0">
    <TR><TD ALIGN="RIGHT">User ID:</TD>
        <TD><INPUT TYPE="TEXT" NAME="nick" SIZE="20" MAXLENGTH="20"
                   VALUE="<ebay:esc value="<%= enteredNick %>"/>"></TD></TR>
    <TR><TD ALIGN="RIGHT">Password:</TD>
        <TD><INPUT TYPE="PASSWORD" NAME="password" SIZE="20"></TD></TR>
    <TR><TD></TD><TD><INPUT TYPE="SUBMIT" NAME="signin" VALUE="Sign In"></TD></TR>
    </TABLE>
    </FORM>
  </TD></TR>
  </TABLE>
</TD>

<TD WIDTH="12"></TD>

<TD WIDTH="50%">
  <TABLE WIDTH="100%" CELLPADDING="6" CELLSPACING="1" BORDER="0" BGCOLOR="#CCCCCC">
  <TR CLASS="hdr"><TD>New to eBay?</TD></TR>
  <TR BGCOLOR="#FFFFFF"><TD>
    <FORM METHOD="POST" ACTION="<%= cp %>/login">
    <TABLE CELLPADDING="3" CELLSPACING="0" BORDER="0">
    <TR><TD ALIGN="RIGHT">User ID:</TD>
        <TD><INPUT TYPE="TEXT" NAME="nick" SIZE="20" MAXLENGTH="20"></TD></TR>
    <TR><TD ALIGN="RIGHT">Password:</TD>
        <TD><INPUT TYPE="PASSWORD" NAME="password" SIZE="20"></TD></TR>
    <TR><TD ALIGN="RIGHT">City, State:</TD>
        <TD><INPUT TYPE="TEXT" NAME="location" SIZE="20" VALUE="Anytown, USA"></TD></TR>
    <TR><TD></TD><TD><INPUT TYPE="SUBMIT" NAME="register" VALUE="Register"></TD></TR>
    </TABLE>
    </FORM>
  </TD></TR>
  </TABLE>
  <BR>
  <TABLE WIDTH="100%" CELLPADDING="6" CELLSPACING="0" BORDER="0" CLASS="note">
  <TR><TD><FONT SIZE="1">
    <B>Demo account:</B> guest / ebay<BR>
    Every seeded member also uses the password <B>ebay</B> &#151; sign in as
    <B>beaniequeen99</B> to see a seller's view, or
    <B>sniper_steve</B> to bid against yourself.
  </FONT></TD></TR>
  </TABLE>
</TD>

</TR></TABLE>

<%@ include file="footer.jsp" %>
