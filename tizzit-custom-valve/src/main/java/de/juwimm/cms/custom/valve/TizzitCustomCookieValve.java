package de.juwimm.cms.custom.valve;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.util.URL;
import org.apache.catalina.valves.ValveBase;

public class TizzitCustomCookieValve extends ValveBase {

	public void invoke(Request request, Response response) throws IOException,
			ServletException {
		try {
			String urlAdress=request.getRequestURL().toString();
			URL url = new URL(urlAdress);
			String host = url.getHost();
			String[] tokens = host.split("\\.");
			//only use this valve if host is not an ip address
			if(!tokens[tokens.length-1].matches("\\d{1,3}")) {
				String domain="";
				if (tokens.length>=2 ){
					domain="."+tokens[tokens.length-2]+"."+tokens[tokens.length-1];
				} else {
					domain=url.getHost();
				}
				ResponseWrapper wr=new ResponseWrapper(response,null,domain);
				request.setResponse(wr);
			}
			getNext().invoke(request, response);
		} finally {
			request.setResponse(response);
		}
	}

	
}
