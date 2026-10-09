<% String pageTitle = "Sell Your Item"; %>
<%@ include file="header.jsp" %>
<%
    List leaves = (List) request.getAttribute("leaves");
    List errors = (List) request.getAttribute("errors");
    AuctionHouse shop = (AuctionHouse) application.getAttribute("ebay.house");

    // On a failed POST the form redisplays with what the member typed.
    String fTitle = request.getParameter("title");
    String fDesc  = request.getParameter("description");
    String fCond  = request.getParameter("condition");
    String fStart = request.getParameter("start");
    String fRes   = request.getParameter("reserve");
    int fCat      = 0;
    int fDays     = 7;
    try { fCat = Integer.parseInt(request.getParameter("cat")); } catch (Exception e) { }
    try { fDays = Integer.parseInt(request.getParameter("days")); } catch (Exception e) { }
    if (fTitle == null) fTitle = "";
    if (fDesc == null) fDesc = "";
    if (fCond == null) fCond = "Used";
    if (fStart == null) fStart = "";
    if (fRes == null) fRes = "";
%>

<FONT SIZE="4"><B>Sell Your Item</B></FONT><BR>
<FONT SIZE="2" COLOR="#666666">Listing is free on this server. It was not in 1999.</FONT>
<BR><BR>

<% if (errors != null && !errors.isEmpty()) { %>
  <TABLE WIDTH="100%" CELLPADDING="6" CELLSPACING="0" BORDER="0" CLASS="err">
  <TR><TD><FONT SIZE="2"><B>Please correct the following:</B><BR>
  <% for (int i = 0; i < errors.size(); i++) { %>
    &#149; <ebay:esc value="<%= (String) errors.get(i) %>"/><BR>
  <% } %>
  </FONT></TD></TR></TABLE><BR>
<% } %>

<FORM METHOD="POST" ACTION="<%= cp %>/sell">
<TABLE WIDTH="100%" CELLPADDING="6" CELLSPACING="1" BORDER="0" BGCOLOR="#CCCCCC">

<TR CLASS="hdr"><TD COLSPAN="2">Describe your item</TD></TR>

<TR BGCOLOR="#FFFFFF"><TD WIDTH="150" ALIGN="RIGHT"><FONT SIZE="2">Title</FONT></TD>
  <TD><INPUT TYPE="TEXT" NAME="title" SIZE="55" MAXLENGTH="60"
             VALUE="<ebay:esc value="<%= fTitle %>"/>">
  <BR><FONT SIZE="1">Up to 60 characters. ALL CAPS and L@@K are traditional.</FONT></TD></TR>

<TR BGCOLOR="#FFFFFF"><TD ALIGN="RIGHT" VALIGN="TOP"><FONT SIZE="2">Description</FONT></TD>
  <TD><TEXTAREA NAME="description" ROWS="6" COLS="55"><ebay:esc value="<%= fDesc %>"/></TEXTAREA></TD></TR>

<TR BGCOLOR="#FFFFFF"><TD ALIGN="RIGHT"><FONT SIZE="2">Category</FONT></TD>
  <TD><SELECT NAME="cat">
  <OPTION VALUE="0">-- choose a category --</OPTION>
  <%
      for (int i = 0; i < leaves.size(); i++) {
          Category c = (Category) leaves.get(i);
          Category parent = shop.getCategory(c.getParentId());
  %>
    <OPTION VALUE="<%= c.getId() %>"<%= fCat == c.getId() ? " SELECTED" : "" %>><%=
      Text.esc(parent.getName()) %> &gt; <%= Text.esc(c.getName()) %></OPTION>
  <% } %>
  </SELECT></TD></TR>

<TR BGCOLOR="#FFFFFF"><TD ALIGN="RIGHT"><FONT SIZE="2">Condition</FONT></TD>
  <TD><SELECT NAME="condition">
    <OPTION VALUE="New"<%= "New".equals(fCond) ? " SELECTED" : "" %>>New</OPTION>
    <OPTION VALUE="Used"<%= "Used".equals(fCond) ? " SELECTED" : "" %>>Used</OPTION>
    <OPTION VALUE="Refurbished"<%= "Refurbished".equals(fCond) ? " SELECTED" : "" %>>Refurbished</OPTION>
  </SELECT></TD></TR>

<TR CLASS="hdr"><TD COLSPAN="2">Set your price</TD></TR>

<TR BGCOLOR="#FFFFFF"><TD ALIGN="RIGHT"><FONT SIZE="2">Starting price</FONT></TD>
  <TD>US $ <INPUT TYPE="TEXT" NAME="start" SIZE="10"
                  VALUE="<ebay:esc value="<%= fStart %>"/>"></TD></TR>

<TR BGCOLOR="#FFFFFF"><TD ALIGN="RIGHT"><FONT SIZE="2">Reserve price</FONT></TD>
  <TD>US $ <INPUT TYPE="TEXT" NAME="reserve" SIZE="10"
                  VALUE="<ebay:esc value="<%= fRes %>"/>">
  <FONT SIZE="1">optional &#151; the item will not sell below this</FONT></TD></TR>

<TR BGCOLOR="#FFFFFF"><TD ALIGN="RIGHT"><FONT SIZE="2">Duration</FONT></TD>
  <TD><SELECT NAME="days">
  <% int[] durations = { 3, 5, 7, 10 };
     for (int i = 0; i < durations.length; i++) { %>
    <OPTION VALUE="<%= durations[i] %>"<%= fDays == durations[i] ? " SELECTED" : "" %>><%=
      durations[i] %> days</OPTION>
  <% } %>
  </SELECT></TD></TR>

<TR BGCOLOR="#FFFFFF"><TD></TD>
  <TD><INPUT TYPE="SUBMIT" VALUE="List My Item"></TD></TR>

</TABLE>
</FORM>

<%@ include file="footer.jsp" %>
