<%@page import="java.sql.*, java.io.*, java.util.*, net.hero.engine.data.dataset.*, net.hero.engine.data.field.*,  net.hero.engine.data.connection.*, net.hero.engine.document.*,net.hero.engine.*, net.hero.engine.document.*, net.hero.engine.content.*, net.hero.engine.export.*, net.hero.engine.document.report.*" contentType="text/xml; charset=utf-8"%><%

	String file = request.getParameter("file");

	Document document = Engine.getInstance().createDocument("", file);
	Content content = new Content();
	ContentBuilder contentBuilder = new ContentBuilder();
	contentBuilder.buildContent(document.getReport(),  content);
	ExportXML export = new ExportXML();
	export.export(content, (OutputStream )response.getOutputStream());
%>
