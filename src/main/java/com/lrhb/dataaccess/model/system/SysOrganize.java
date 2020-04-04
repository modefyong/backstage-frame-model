package com.lrhb.dataaccess.model.system;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysOrganize implements Serializable{
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYS_ORGANIZE_TEST.ID
     *
     * @mbggenerated Thu Dec 07 15:21:01 CST 2017
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYS_ORGANIZE_TEST.PARENT_ID
     *
     * @mbggenerated Thu Dec 07 15:21:01 CST 2017
     */
    private String parentId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYS_ORGANIZE_TEST.ORGANIZE_NAME
     *
     * @mbggenerated Thu Dec 07 15:21:01 CST 2017
     */
    private String organizeName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYS_ORGANIZE_TEST.ORGANIZE_CODE
     *
     * @mbggenerated Thu Dec 07 15:21:01 CST 2017
     */
    private String organizeCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYS_ORGANIZE_TEST.TREE_PATH
     *
     * @mbggenerated Thu Dec 07 15:21:01 CST 2017
     */
    private String treePath;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYS_ORGANIZE_TEST.CREATE_DATE
     *
     * @mbggenerated Thu Dec 07 15:21:02 CST 2017
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYS_ORGANIZE_TEST.UPDATE_DATE
     *
     * @mbggenerated Thu Dec 07 15:21:02 CST 2017
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateDate;

    /**
     * 层级，根为1
     * */
    private Integer level;

}