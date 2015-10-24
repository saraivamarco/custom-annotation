package pt.spike.nfo.interceptor;

import java.lang.reflect.Method;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import pt.spike.nfo.HasAccess;

/**
 * 
 * @author Marco
 *
 */
@Access
@Interceptor
public class AccessParser {

	@AroundInvoke
	public Object hasAccess(InvocationContext ctx) throws Exception {
		parse(ctx.getMethod());

		Object[] parameters = ctx.getParameters();
		String param = (String) parameters[0];
		param = param.toLowerCase();
		parameters[0] = param;
		ctx.setParameters(parameters);
		try {
			return ctx.proceed();
		} catch (Exception e) {
			System.out.println("Error calling ctx.proceed in hasAccess()");
			return null;
		}
	}

	public void parse(Method method) throws Exception {
		if (method.isAnnotationPresent(HasAccess.class)) {
			HasAccess access = method.getAnnotation(HasAccess.class);
			String info = access.value().name();
			System.out.println("This is an "+info+" user.");
			if(info.equals("ANONYMOUS")){
				throw new Exception(info+" user does not have access to this function.");
			}			
		}
	}



}
