package cn.leepon.mapper;

import cn.leepon.po.UserInfo;

/**   
 * This class is used for ...   
 * @author leepon1990  
 * @version   
 *       1.0, 2016年9月1日 下午6:04:48   
 */
public interface UserMapper {

	UserInfo getUserInfoByName(String username); 

}
