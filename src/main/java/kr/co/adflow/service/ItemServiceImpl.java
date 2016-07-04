package kr.co.adflow.service;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.co.adflow.domain.Item;



@Service("sampleService")
public class ItemServiceImpl implements ItemService{
	Logger log = Logger.getLogger(this.getClass());
	


	@Override
	public void insertItem(Item item) {
		// TODO Auto-generated method stub
		
	}
}
