CREATE TABLE `game_rescue` (
  `Sno` int(11) NOT NULL auto_increment,
  `fname` varchar(50) NOT NULL,
  `lname` varchar(50) NOT NULL,
  `universityid` varchar(50) default NULL,
  `email` varchar(50) NOT NULL,
  `pwd` varchar(50) NOT NULL,
  `university` varchar(50) NOT NULL,
  `academic` varchar(20) NOT NULL,
  `sem` int(11) NOT NULL,
  PRIMARY KEY  (`Sno`),
  UNIQUE KEY `email_ind` (`email`),
  UNIQUE KEY `universityid` (`universityid`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=latin1;

CREATE TABLE `rescue_logger` (
  `Sno` int(11) NOT NULL auto_increment,
  `email` varchar(50) NOT NULL,
  `gameid` varchar(20) NOT NULL,
  `time` varchar(30) NOT NULL,
  PRIMARY KEY  (`Sno`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=latin1;

CREATE TABLE `rescue_scores` (
  `gameid` varchar(20) NOT NULL,
  `level` int(11) NOT NULL,
  `steps` int(11) NOT NULL,
  `bonus` int(11) default NULL,
  `timecapture` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
