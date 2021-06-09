package com.kagwi;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class PayeServer {

	HttpServer server;
	ThreadPoolExecutor threadPoolExecutor;

	public PayeServer() {
		try {
			threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
			server = HttpServer.create(new InetSocketAddress("localhost", 8001), 0);
			server.createContext("/tax", new MyHttpHandler());
			server.setExecutor(threadPoolExecutor);
			server.start();
			System.out.println("Server started on port 8001");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		new PayeServer();
	}
}

class MyHttpHandler implements HttpHandler {

	public MyHttpHandler() {

	}

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		String requestParamValue = null;
		if ("GET".equals(httpExchange.getRequestMethod())) {
			requestParamValue = handleGetRequest(httpExchange);
		} else if ("POST".equals(httpExchange)) {
			requestParamValue = handlePostRequest(httpExchange);
		}
		handleResponse(httpExchange, requestParamValue);
	}

	private String handleGetRequest(HttpExchange httpExchange) {
		return httpExchange.getRequestURI().toString().split("\\?")[1].split("=")[1];
	}

	private void handleResponse(HttpExchange httpExchange, String requestParamValue) throws IOException {
		OutputStream outputStream = httpExchange.getResponseBody();
		StringBuilder htmlBuilder = new StringBuilder();
		htmlBuilder.append("<html>").append("<body>").append("<h1>").append("Net Salary is: ").append(new PayeCalc().calculateNetPay(requestParamValue))
				.append("</h1>").append("</body>").append("</html>");
		String htmlResponse = htmlBuilder.toString();
		httpExchange.sendResponseHeaders(200, htmlResponse.length());
		outputStream.write(htmlResponse.getBytes());
		outputStream.flush();
		
		outputStream.close();
	}

	public String handlePostRequest(HttpExchange httpExchange) {
		return "";
	}

}