<%--
  Page chrome, pulled in with <%@ include file="header.jsp" %>.

  A static include: Jasper splices this source into the including page before
  compiling, so the two share one servlet, one set of locals, and one page
  buffer. That is why a page can declare String pageTitle before including
  this file and have it just work -- and why two pages that both declare "cp"
  would collide. Dynamic <jsp:include> is a runtime call and shares nothing
  but request scope; see itemrows.jsp for that one.
--%>
<%@ page import="mx.jehualli.ebay.service.*" %>
<%@ page import="mx.jehualli.ebay.util.*" %>
<%@ page import="mx.jehualli.ebay.model.*" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://ebay.example.com/taglib" prefix="ebay" %>
<%
    String cp = request.getContextPath();
    String meNick = (String) session.getAttribute("nick");
%>
<HTML>
<HEAD>
<TITLE>eBay - <%= pageTitle %></TITLE>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=iso-8859-1">
<STYLE TYPE="text/css">
BODY,TD,TH,INPUT,SELECT,TEXTAREA{font-family:Verdana,Arial,Helvetica,sans-serif;font-size:11px}
A{color:#0000CC}
A:visited{color:#551A8B}
.nav{background:#EFEFEF;border-top:1px solid #999999;border-bottom:1px solid #999999}
.nav A{color:#0000CC;text-decoration:none;font-weight:bold}
.hdr{background:#CCCCCC;font-weight:bold}
.hdr A{color:#000080}
.note{background:#FFFFCC;border:1px solid #CCCC66}
.err{background:#FFEEEE;border:1px solid #CC6666;color:#CC0000}
.logo{font-size:30px;font-weight:bold;font-family:Arial,Helvetica,sans-serif;letter-spacing:-2px}
</STYLE>
</HEAD>
<BODY BGCOLOR="#FFFFFF" TEXT="#000000" MARGINHEIGHT="4" MARGINWIDTH="4" LINK="#0000CC" VLINK="#551A8B">

<TABLE WIDTH="760" CELLPADDING="0" CELLSPACING="0" BORDER="0">

<TR>
  <TD VALIGN="BOTTOM">
    <A HREF="<%= cp %>/" STYLE="text-decoration:none"><SPAN CLASS="logo"><SPAN STYLE="color:#E53238">e</SPAN><SPAN
      STYLE="color:#0064D2">b</SPAN><SPAN STYLE="color:#F5AF02">a</SPAN><SPAN
      STYLE="color:#86B817">Y</SPAN></SPAN></A>
    <FONT SIZE="1" COLOR="#666666">&nbsp;The World's Online Marketplace<SUP>SM</SUP></FONT>
  </TD>
  <TD VALIGN="BOTTOM" ALIGN="RIGHT">
    <FONT SIZE="1">
    <ebay:loggedIn>
      Hello <B><%= Text.esc(meNick) %></B>!
      (<A HREF="<%= cp %>/logout">sign out</A>)
    </ebay:loggedIn>
    <ebay:anonymous>
      Welcome! <A HREF="<%= cp %>/login"><B>sign in</B></A> or
      <A HREF="<%= cp %>/login">register</A>
    </ebay:anonymous>
    &nbsp;|&nbsp; <A HREF="<%= cp %>/">home</A>
    &nbsp;|&nbsp; <A HREF="<%= cp %>/myebay">my eBay</A>
    &nbsp;|&nbsp; <A HREF="<%= cp %>/feedback?user=<%= meNick == null ? "guest" : meNick %>">feedback</A>
    </FONT>
  </TD>
</TR>

<TR><TD COLSPAN="2" HEIGHT="4"></TD></TR>

<TR><TD COLSPAN="2" CLASS="nav">
  <TABLE WIDTH="100%" CELLPADDING="4" CELLSPACING="0" BORDER="0"><TR>
    <TD NOWRAP><FONT SIZE="2">
      <A HREF="<%= cp %>/browse">Browse</A> &nbsp;|&nbsp;
      <A HREF="<%= cp %>/sell">Sell</A> &nbsp;|&nbsp;
      <A HREF="<%= cp %>/myebay">Services</A> &nbsp;|&nbsp;
      <A HREF="<%= cp %>/search">Search</A> &nbsp;|&nbsp;
      <A HREF="<%= cp %>/help">Help</A> &nbsp;|&nbsp;
      <A HREF="<%= cp %>/browse?sort=1">Community</A>
    </FONT></TD>
    <TD ALIGN="RIGHT" NOWRAP>
      <FORM METHOD="GET" ACTION="<%= cp %>/search" STYLE="margin:0">
      <INPUT TYPE="TEXT" NAME="q" SIZE="18">
      <INPUT TYPE="SUBMIT" VALUE="Find It!">
      </FORM>
    </TD>
  </TR></TABLE>
</TD></TR>

<TR><TD COLSPAN="2" ALIGN="CENTER">
  <TABLE WIDTH="468" HEIGHT="60" CELLPADDING="0" CELLSPACING="0" BORDER="1"
         BGCOLOR="#FFFFCC" STYLE="margin:8px 0"><TR>
    <TD ALIGN="CENTER"><FONT SIZE="1" COLOR="#999999">
      [ 468 x 60 banner &#151; FREE INTERNET ACCESS! CLICK HERE ]
    </FONT></TD>
  </TR></TABLE>
</TD></TR>

</TABLE>

<TABLE WIDTH="760" CELLPADDING="0" CELLSPACING="0" BORDER="0"><TR><TD>
