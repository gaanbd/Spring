package com.techmango.springbootadminserver;

import de.codecentric.boot.admin.server.domain.values.StatusInfo;
import de.codecentric.boot.admin.server.web.ApplicationsController.Application;

public class ClientApplicationStatusChangedEvent extends ClientApplicationEvent {
	private static final long serialVersionUID = 1L;
	private final StatusInfo from;
	private final StatusInfo to;

	public ClientApplicationStatusChangedEvent(Application application, StatusInfo from, StatusInfo to) {
		super(application, "STATUS_CHANGE");
		this.from = from;
		this.to = to;
	}

	public StatusInfo getFrom() {
		return from;
	}

	public StatusInfo getTo() {
		return to;
	}
}
