package com.ebiz;

import java.util.List;
import static java.util.Collections.singletonList;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.timeSheet.model.usermgmt.UserWithRole;
import com.timeSheet.rest.UserMgmtController;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class EbizWapiApplicationTests {

	@Autowired
	private MockMvc mvc;
	@MockBean
	private UserMgmtController userController;
	
	@Test
	public void contextLoads() {
		List<UserWithRole> user = null;
		
//	       mvc.perform(get( "all")
//	               .with(id)
//	               .contentType(APPLICATION_JSON))
//	               .andExpect(status().isOk())
//	               .andExpect(jsonPath("$", hasSize(1)))
//	               .andExpect(jsonPath("$[0].city", is(user)));
	}

}
