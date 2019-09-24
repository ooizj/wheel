package me.ooi.wheel.query;

import java.util.Date;

import lombok.Data;

/**
 * 
 * @author jun.zhao
 * <p>date:2017年6月25日</p>
 */
//@Entity
//@Table(name="User")
@Data
public class User2 {
	
	private Integer id ; 
	private String name ; 
	private String sex ; 
	private String describle ; 
	private Integer age ;
	private Date bothday ; 
	
//	@Transient
//	private User2 parent ; 
	 
//	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
}
