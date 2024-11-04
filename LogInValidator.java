package com.samuel.abb.server.services.login;
import org.springframework.validation.Validator;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;




import com.polaris.domain.User;
import com.polaris.repository.idao.IUserDaoHibernate;

public class LogInValidator implements Validator {
    

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    private IUserDaoHibernate userDaoHibernate;
    
    public void setUserDaoHibernate(IUserDaoHibernate userDaoHibernate) {
		this.userDaoHibernate = userDaoHibernate;
	}

	public boolean supports(Class clazz) {
        return LogIn.class.equals(clazz);
    }

    public void validate(Object obj, Errors errors) {
    	LogIn login = (LogIn) obj;
    	
    	User user=null;
	    
    	if(!(login.getUserName()=="") && !(login.getPassWord()=="") )
    	{
	        try {
				user = userDaoHibernate.getUserByNameAndPassword(login.getUserName(),
						login.getPassWord());
			} catch (Exception e) {
				logger.info(e.getMessage());
			}
			
			if(user==null)
	        {
	        	 errors.rejectValue("inValidUser", "", null, "The username or password you entered is incorrect.");
	        }
    	}
        else {
            logger.info("Validating with " + login + ": " + login.getUserName());
            if (login.getUserName()== null || login.getUserName()== "") {
                errors.rejectValue("userName", null,
                    null, "!Required");
            }
            if (login.getPassWord()== null || login.getPassWord()== "") {
                errors.rejectValue("passWord", null,
                    null, "!Required");
            }
        }
        
    }

   

}