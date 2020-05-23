package com.app.fullyloaded.Models;

public class TicketSetModel {

    String TicketFrom, TicketTo;

    public TicketSetModel(String TicketFrom, String TicketTo) {
        this.TicketFrom = TicketFrom;
        this.TicketTo = TicketTo;
    }

    public String getTicketFrom() {
        return TicketFrom;
    }

    public void setTicketFrom(String ticketFrom) {
        TicketFrom = ticketFrom;
    }

    public String getTicketTo() {
        return TicketTo;
    }

    public void setTicketTo(String ticketTo) {
        TicketTo = ticketTo;
    }
}
