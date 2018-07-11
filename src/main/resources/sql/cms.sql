SET FOREIGN_KEY_CHECKS=0;

-- 用户表
DROP TABLE IF EXISTS ry_user;
CREATE TABLE ry_user (
  userid	VARCHAR(32) NOT NULL COMMENT	'用户ID',
  username	VARCHAR(50) NOT NULL COMMENT	'用户名',
  password	VARCHAR(100) NOT NULL COMMENT	'密码',
  pre_logintime DATETIME DEFAULT NULL COMMENT	'上次登录时间',
  ipadress	VARCHAR(20) DEFAULT NULL COMMENT	'IP地址',
  available	INT(1) DEFAULT 0 NOT NULL COMMENT '可用状态',
  PRIMARY KEY (userid)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '用户表';

insert into ry_user values(UUID(),'admin','1','2017-3-13','192.168.1.22','1');

-- 角色表
DROP TABLE IF EXISTS ry_role;
CREATE TABLE ry_role (
  roleid	VARCHAR(32) NOT NULL COMMENT	'角色ID',
  rolename	VARCHAR(50) NOT NULL COMMENT	'角色名',
  PRIMARY KEY (roleid)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '角色表';

-- 人员角色表
DROP TABLE IF EXISTS ry_userrole;
CREATE TABLE ry_userrole (
  userid	VARCHAR(32) NOT NULL COMMENT	'用户ID',
  roleid	VARCHAR(32) NOT NULL COMMENT	'角色ID',
  PRIMARY KEY (userid,roleid)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '用户角色表';

-- 权限表
DROP TABLE IF EXISTS ry_permission;
CREATE TABLE ry_permission (
  per_id	VARCHAR(32) NOT NULL COMMENT	'权限ID',
  per_name	VARCHAR(50) DEFAULT NULL COMMENT	'权限名',
  parentid	VARCHAR(32) DEFAULT NULL COMMENT	'所属上级权限ID',
  parentname	VARCHAR(50) DEFAULT NULL COMMENT	'所属上级权限名称',
  url       VARCHAR(255) DEFAULT NULL COMMENT '菜单链接',
  icon      VARCHAR(100) DEFAULT NULL  COMMENT '图标',
  orders     INT DEFAULT 0 COMMENT "所在目录顺序",
  isheader      INT DEFAULT 0 COMMENT '0表示有父级,1表示没有父级',
  ismenuorpoint      INT DEFAULT 0 COMMENT '0代表菜单权限,1代表页面权限点',
  PRIMARY KEY (per_id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '权限表';

-- 角色权限表
DROP TABLE IF EXISTS ry_roleper;
CREATE TABLE ry_roleper (
  roleid	VARCHAR(32) NOT NULL COMMENT	'角色ID',
  per_id	VARCHAR(32) NOT NULL COMMENT	'权限ID',
  PRIMARY KEY (roleid,per_id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '角色权限表';


-- =======================================================================================================

-- 商品团购属性表
DROP TABLE IF EXISTS yw_goodsattribute;
CREATE TABLE yw_goodsattr (
  attrid	VARCHAR(32) NOT NULL COMMENT	'商品团购属性id',
  goodsbasicid	VARCHAR(32) DEFAULT null COMMENT	'商品基础属性id',
  groupbuymincount	INT DEFAULT 0 COMMENT	'可团购最低人数',
  schoolid	VARCHAR(32) DEFAULT null COMMENT	'学校id',
  schoolname	VARCHAR(30) DEFAULT null COMMENT	'学校名称',
  PRIMARY KEY (attrid)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '商品团购属性表';

-- 商品基础属性表
DROP TABLE IF EXISTS yw_goodsbasic;
CREATE TABLE yw_goodsbasic (
  goodsbasicid	VARCHAR(32) NOT NULL COMMENT	'商品团购属性id',
  goodsname	VARCHAR(32) DEFAULT null COMMENT	'商品基础属性id',
  goodsexplain	INT DEFAULT 0 COMMENT	'可团购最低人数',
  goodsimg	VARCHAR(255) DEFAULT null COMMENT	'照片地址',
  unitprice	FLOAT DEFAULT 0.0 COMMENT	'商品单价',
  groupbuytime	VARCHAR(50) DEFAULT NULL COMMENT	'可团购时间',
  costprice	FLOAT DEFAULT 0.0 COMMENT	'成本价',
  goodsstatus	INT DEFAULT 0 COMMENT	'商品属性',
  createpersonid	VARCHAR(32) DEFAULT null  COMMENT	'创建人id',
  creater	VARCHAR(30) DEFAULT null COMMENT	'创建人名称',
  createtime	DATETIME DEFAULT NULL COMMENT	'创建时间',
  PRIMARY KEY (goodsbasicid)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '商品团购属性表';

-- 商家属性表
DROP TABLE IF EXISTS yw_merchant;
CREATE TABLE yw_merchant (
  merchantid	VARCHAR(32) NOT NULL COMMENT	'商家id',
  merchantname	VARCHAR(32) DEFAULT null COMMENT	'商家名称',
  merchantaddr	VARCHAR(60) DEFAULT null COMMENT	'商家地址',
  merchanttel	VARCHAR(30) DEFAULT null COMMENT	'商家电话',
  merchantstatus INT	DEFAULT 0 COMMENT	'商家状态(是否休息)',
  PRIMARY KEY (merchantid)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '商家属性表';


-- 订单表
DROP TABLE IF EXISTS yw_orders;
CREATE TABLE yw_orders (
  ordersid	VARCHAR(32) NOT NULL COMMENT	'订单id',
  ordertime	VARCHAR(32) DEFAULT null COMMENT	'下单时间',
  orderspersonid	VARCHAR(60) DEFAULT null COMMENT	'下单人id',
  orderpersonname	VARCHAR(30) DEFAULT null COMMENT	'下单人名称',
  goodsid INT	DEFAULT 0 COMMENT	'商品id',
  goodsnum	VARCHAR(60) DEFAULT null COMMENT	'商品数量',
  bringgoodsaddr	VARCHAR(60) DEFAULT null COMMENT	'取餐地址',
  sendtime	VARCHAR(60) DEFAULT null COMMENT	'配送时间',
  bringgoodsstatus	VARCHAR(60) DEFAULT null COMMENT	'是否取餐',
  paymentstatus	VARCHAR(60) DEFAULT null COMMENT	'是否付款',
  orderssumprice	VARCHAR(60) DEFAULT null COMMENT	'订单总价',
  PRIMARY KEY (ordersid)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '订单表';

-- 学生表
DROP TABLE IF EXISTS yw_student;
CREATE TABLE yw_student (
  studentid	VARCHAR(32) NOT NULL COMMENT	'学生id',
  studentname	VARCHAR(32) DEFAULT null COMMENT	'名称',
  studentphone	VARCHAR(30) DEFAULT null COMMENT	'电话',
  schoolid	VARCHAR(32) DEFAULT null COMMENT	'学校id',
  schoolname VARCHAR(32) DEFAULT null COMMENT	'学校名称',
  major	VARCHAR(20) DEFAULT null COMMENT	'专业',
  sex	INT DEFAULT 0 COMMENT	'性别',
  status	INT DEFAULT 0  COMMENT	'状态',
  wxoppenid	VARCHAR(60) DEFAULT null COMMENT	'微信oppenid',
  grade	INT DEFAULT 0  COMMENT	'级别',
  PRIMARY KEY (studentid)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '学生表';

-- 学校表
DROP TABLE IF EXISTS yw_school;
CREATE TABLE yw_school (
  schoolid	VARCHAR(32) NOT NULL COMMENT	'学校id',
  schoolname	VARCHAR(32) DEFAULT null COMMENT	'学校名称',
  schooladdr	VARCHAR(30) DEFAULT null COMMENT	'学校地址',
  status	INT DEFAULT 0  COMMENT	'状态',
  campus	VARCHAR(30) DEFAULT null  COMMENT	'校区',
  PRIMARY KEY (schoolid)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '学校表';
