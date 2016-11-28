namespace java com.thrift.tools
service TestThrift{
 string qury(1:string name ,2:i32 age)
// string qryNpnoById(1:i32 buildId,2:i32 floorId,3:i32 netType,4:i32 logType)
}