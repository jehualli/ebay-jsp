<% String pageTitle = "My eBay"; %>
<%@ include file="header.jsp" %>
<%
    String view = (String) request.getAttribute("view");
    User me = (User) request.getAttribute("me");
    String[][] tabs = {
        { "bidding",  "Bidding"  },
        { "watching", "Watching" },
        { "won",      "Won"      },
        { "selling",  "Selling"  },
        { "sold",     "Closed"   }
    };
%>

<FONT SIZE="4"><B>My eBay</B></FONT>
<FONT SIZE="2">&nbsp;&#151;&nbsp;<ebay:feedback user="<%= me %>"/></FONT>
<BR><BR>

<TABLE CELLPADDING="0" CELLSPACING="2" BORDER="0"><TR>
<%
    for (int i = 0; i < tabs.length; i++) {
        boolean on = tabs[i][0].equals(view);
%>
  <TD BGCOLOR="<%= on ? "#CCCCCC" : "#EFEFEF" %>" STYLE="border:1px solid #999999">
    <TABLE CELLPADDING="4" CELLSPACING="0" BORDER="0"><TR><TD>
    <FONT SIZE="2"><% if (on) { %><B><%= tabs[i][1] %></B><% } else { %>
      <A HREF="<%= cp %>/myebay?view=<%= tabs[i][0] %>"><%= tabs[i][1] %></A>
    <% } %></FONT>
    </TD></TR></TABLE>
  </TD>
<% } %>
</TR></TABLE>

<TABLE WIDTH="100%" CELLPADDING="6" CELLSPACING="0" BORDER="0" CLASS="note"><TR><TD>
<FONT SIZE="1">
<% if ("bidding".equals(view)) { %>
  Auctions you have bid on that are still running. A bold price means you are
  winning; if someone else's proxy has passed your maximum, bid again.
<% } else if ("watching".equals(view)) { %>
  Items you added to your watch list. Nothing is committed until you bid.
<% } else if ("won".equals(view)) { %>
  Auctions you won. Contact the seller within three days.
<% } else if ("selling".equals(view)) { %>
  Your listings that are still running.
<% } else { %>
  Your listings that have ended.
<% } %>
</FONT>
</TD></TR></TABLE>
<BR>

<jsp:include page="itemrows.jsp">
  <jsp:param name="list" value="items"/>
  <jsp:param name="showSeller" value="1"/>
</jsp:include>

<BR>
<FONT SIZE="2"><A HREF="<%= cp %>/sell"><B>Sell an item</B></A> &nbsp;|&nbsp;
<A HREF="<%= cp %>/browse">Browse the categories</A></FONT>

<%@ include file="footer.jsp" %>
