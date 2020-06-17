CREATE TABLE IF NOT EXISTS `t_user` (
`id` INTEGER  PRIMARY KEY AUTO_INCREMENT,
`active_status` VARCHAR(1) NOT NULL,
`email` VARCHAR(25),
`login_name` VARCHAR(255) NOT NULL,
`password` VARCHAR(255),
`phone` VARCHAR(25),
`real_name` VARCHAR(255),
`role` VARCHAR(10) NOT NULL
);