package net.rrm.ehour.user.domain;

import java.util.Set;

import net.rrm.ehour.domain.DomainObject;

import org.apache.commons.lang.builder.CompareToBuilder;

// Generated Sep 26, 2006 11:58:17 PM by Hibernate Tools 3.2.0.beta7


/**
 * UserDepartment generated by hbm2java
 */
public class UserDepartment extends DomainObject<Integer, UserDepartment>
{

	// Fields    

	/**
	 * 
	 */
	private static final long serialVersionUID = 7802944013593352L;

	
	/**
	 * @uml.property  name="departmentId"
	 */
	private Integer departmentId;

	/**
	 * @uml.property  name="name"
	 */
	private String name;

	/**
	 * @uml.property  name="code"
	 */
	private String code;
	
	private Set		users;

	// Constructors

	/** default constructor */
	public UserDepartment()
	{
	}
	
	/**
	 * id only constructor
	 * @param departmentId
	 */
	public UserDepartment(Integer departmentId)
	{
		this.departmentId = departmentId;	
	}

	/** minimal constructor */
	public UserDepartment(Integer departmentId, String name, String code)
	{
		this.departmentId = departmentId;
		this.name = name;
		this.code = code;
	}

	/** full constructor */
	public UserDepartment(Integer departmentId, String name, String code, Set users)
	{
		this.departmentId = departmentId;
		this.name = name;
		this.code = code;
		this.users = users;
	}

	// Property accessors
	/**
	 * @return  the departmentId
	 * @uml.property  name="departmentId"
	 */
	public Integer getDepartmentId()
	{
		return this.departmentId;
	}

	/**
	 * @param departmentId  the departmentId to set
	 * @uml.property  name="departmentId"
	 */
	public void setDepartmentId(Integer departmentId)
	{
		this.departmentId = departmentId;
	}

	/**
	 * @return  the name
	 * @uml.property  name="name"
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * @param name  the name to set
	 * @uml.property  name="name"
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return  the code
	 * @uml.property  name="code"
	 */
	public String getCode()
	{
		return this.code;
	}

	/**
	 * @param code  the code to set
	 * @uml.property  name="code"
	 */
	public void setCode(String code)
	{
		this.code = code;
	}

	public Set getUsers()
	{
		return users;
	}

	public void setUsers(Set users)
	{
		this.users = users;
	}

	@Override
	public Integer getPK()
	{
		return departmentId;
	}

	/**
	 * @see java.lang.Comparable#compareTo(Object)
	 */
	public int compareTo(UserDepartment object)
	{
		return new CompareToBuilder()
			.append(this.name, object.name)
			.append(this.code, object.code)
			.append(this.departmentId, object.departmentId).toComparison();
	}
}
