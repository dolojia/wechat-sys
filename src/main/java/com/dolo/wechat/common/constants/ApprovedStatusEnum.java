package com.dolo.wechat.common.constants;

/**
 * 
 * @类名： ApprovedStatusEnum.java 
 * @描述：审核的几种状态
 * @作者： mxyanx
 * @修改日期： 2014年9月11日
 */
public enum ApprovedStatusEnum
{
    UNSUBMIT("unsubmit","未提交"),
    SUBMIT("submit","待审核"),
    UNAPPROVED("unapproved","审核未通过"),
    APPROVED("approved","审核已通过");
    
    private String status;
    
    private String statusDesc;
    
    private ApprovedStatusEnum(String status, String statusDesc){
        this.status = status;
        this.statusDesc = statusDesc;
    }
    
    /**
     * 
     * 功能描述：根据状态名称返回状态描述
     * @param status
     * @return
     */
    public static String getStatusDescByName(String status){
        for(ApprovedStatusEnum approvedStatusEnum :ApprovedStatusEnum.values()){
            if(status.equalsIgnoreCase(approvedStatusEnum.status)){
                return approvedStatusEnum.statusDesc;
            }
        }
        return null;
    }
    
    /**
     * 
     * 功能描述：根据状态描述返回状态名称
     * @param statusDesc
     * @return
     */
    public static String getStatusByDesc(String statusDesc){
        for(ApprovedStatusEnum approvedStatusEnum :ApprovedStatusEnum.values()){
            if(statusDesc.equalsIgnoreCase(approvedStatusEnum.statusDesc)){
                return approvedStatusEnum.status;
            }
        }
        return null;
    }
    
    
    public static void main(String[] args)
    {
        System.out.println(getStatusDescByName("submit"));
        System.out.println(getStatusByDesc("已提交"));
    }

}
