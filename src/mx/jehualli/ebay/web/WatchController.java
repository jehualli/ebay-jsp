package mx.jehualli.ebay.web;

import mx.jehualli.ebay.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** Add or remove a listing from the watch list, then go back where you were. */
public class WatchController extends BaseController {

    protected void handle(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        User me = me(req);
        long id = longParam(req, "id", -1L);
        if (me != null && house().getItem(id) != null) {
            Long key = Long.valueOf(id);
            if (!me.getWatching().remove(key)) me.getWatching().add(key);
        }

        String back = param(req, "back");
        if (back.length() == 0) back = "/item?id=" + id;
        redirect(req, res, back);
    }
}
