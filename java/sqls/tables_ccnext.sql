CREATE TABLE `users` (
  `Sno` int(11) NOT NULL auto_increment,
  `fname` varchar(50) NOT NULL,
  `lname` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `pwd` varchar(50) NOT NULL,
  PRIMARY KEY  (`Sno`),
  UNIQUE KEY `email_ind` (`email`),
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=latin1;

CREATE TABLE `user_details` (
  `Sno` int(11) NOT NULL auto_increment,
  `universityid` varchar(50) default NULL,
  `university` varchar(50) NOT NULL,
  `academic` varchar(20) NOT NULL,
  `sem` int(11) NOT NULL,
  PRIMARY KEY  (`Sno`),
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=latin1;

CREATE TABLE `logger` (
  `Sno` int(11) NOT NULL auto_increment,
  `email` varchar(50) NOT NULL,
  `gameid` varchar(20) NOT NULL,
  `time` varchar(30) NOT NULL,
  PRIMARY KEY  (`Sno`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=latin1;

CREATE TABLE `scores` (
  `gameid` varchar(20) NOT NULL,
  `level` int(11) NOT NULL,
  `steps` int(11) NOT NULL,
  `bonus` int(11) default NULL,
  `timecapture` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
