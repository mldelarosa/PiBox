package com.pibox.api.rest;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.pibox.bean.SessionBean;
import com.pibox.bean.UserBean;
import com.pibox.dao.SessionDAO;
import com.pibox.dao.UserDAO;

@Path("rest/sessions")
public class SessionRest {
	
	/* CRUD OPERATIONS*/
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllSessions() {
		Response response = null;
		SessionDAO sessionDao = new SessionDAO();
		List<SessionBean> sessionsToReturn;
		try {
			sessionsToReturn = sessionDao.readAllSessions();
			if(sessionsToReturn != null) {
				response = Response.ok(sessionsToReturn).build();
			} else {
				response = Response.status(404).build();
			}
			
		} catch (SQLException e) {
			response = Response.status(500).build();
			e.printStackTrace();
		}
		return response;
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response getSessionById(@PathParam("id") int id) {
		Response response = null;
		SessionDAO sessionDao = new SessionDAO();
		SessionBean sessionToReturn;
		try {
			sessionToReturn = sessionDao.readSession(id);
			if(sessionToReturn != null) {
				response = Response.ok(sessionToReturn).build();
			} else {
				response = Response.status(404).build();
			}
			
		} catch (SQLException e) {
			response = Response.status(500).build();
			e.printStackTrace();
		}
		return response;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response createSession( @FormParam("name") String name,
								@FormParam("activity") String activity,
								@DefaultValue("wait") @FormParam("status") String status) {
		Response response = null;
		
		SessionBean sessionToCreate = new SessionBean(name, activity, status);
		SessionBean sessionToReturn;
		try {
			SessionDAO sessionDao = new SessionDAO();
			sessionToReturn = sessionDao.createSession(sessionToCreate);
			response = Response.ok(sessionToReturn).build();
		} catch (SQLException e) {
			response = Response.status(500).build();
			e.printStackTrace();
		}
		return response;
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateSession( @FormParam("id") int id,
									@FormParam("name") String name,
									@FormParam("activity") String activity,
									@FormParam("status") String status) {
		Response response = null;
		
		SessionDAO sessionDao = new SessionDAO();
		SessionBean sessionToUpdate = new SessionBean();
		
		sessionToUpdate.setId(id);
		sessionToUpdate.setName(name);
		sessionToUpdate.setActivity(activity);
		sessionToUpdate.setStatus(status);
		SessionBean sessionToReturn = null;
		try {
			sessionToReturn = sessionDao.updateSession(sessionToUpdate);
			response = Response.ok(sessionToReturn).build();
		} catch (SQLException e) {
			response = Response.status(500).build();
			e.printStackTrace();
		}
		return response;
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteSession( @FormParam("id") int sessionId) {
		Response response = null;
		SessionDAO sessionDao = new SessionDAO();
		try {
			sessionDao.deleteSession(sessionId);
			response = Response.noContent().build();
		} catch (SQLException e) {
			response = Response.status(500).build();
			e.printStackTrace();
		}
		return response;
	}
}
