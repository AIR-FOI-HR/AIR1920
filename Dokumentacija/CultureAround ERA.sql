CREATE TABLE `korisnik` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`ime` varchar(255) NOT NULL,
`prezime` varchar(255) NOT NULL,
`korisnicko ime` varchar(255) NOT NULL,
`email` varchar(255) NOT NULL,
`lozinka` varchar(255) NOT NULL,
PRIMARY KEY (`id`) 
);
CREATE TABLE `destinacija` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`naziv` varchar(255) NOT NULL,
`opis` varchar(255) NULL,
`imgUrl` varchar(255) NULL,
PRIMARY KEY (`id`) 
);
CREATE TABLE `znamenitost` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`id_destinacija` varchar(255) NOT NULL,
`naziv` varchar(255) NOT NULL,
`adresa` varchar(255) NULL,
`tip` varchar(255) NULL,
`opis` varchar(255) NULL,
`imgUrl` varchar(255) NULL,
`longitude` bigint NULL,
`latitude` bigint NULL,
PRIMARY KEY (`id`) 
);
CREATE TABLE `lokacija` (
);
CREATE TABLE `komentar` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`id_korisnik` int(11) NOT NULL,
`id_znamenitost` int(11) NOT NULL,
`opis` varchar(1024) NOT NULL,
`ocjena` int NULL,
PRIMARY KEY (`id`) 
);
CREATE TABLE `favorites` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`id_korisnik` int(11) NULL,
`id_znamenitost` int(11) NULL,
PRIMARY KEY (`id`) 
);
CREATE TABLE `korisnik_destinacija` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`id_korisnik` int(11) NOT NULL,
`id_destinacija` int(11) NOT NULL,
PRIMARY KEY (`id`) 
);

ALTER TABLE `destinacija` ADD CONSTRAINT `fk_destinacija_znamenitost_1` FOREIGN KEY (`id`) REFERENCES `znamenitost` (`id_destinacija`);
ALTER TABLE `korisnik` ADD CONSTRAINT `fk_korisnik_komentar_1` FOREIGN KEY (`id`) REFERENCES `komentar` (`id_korisnik`);
ALTER TABLE `znamenitost` ADD CONSTRAINT `fk_znamenitost_komentar_1` FOREIGN KEY (`id`) REFERENCES `komentar` (`id_znamenitost`);
ALTER TABLE `korisnik` ADD CONSTRAINT `fk_korisnik_favorites_1` FOREIGN KEY (`id`) REFERENCES `favorites` (`id_korisnik`);
ALTER TABLE `znamenitost` ADD CONSTRAINT `fk_znamenitost_favorites_1` FOREIGN KEY (`id`) REFERENCES `favorites` (`id_znamenitost`);
ALTER TABLE `korisnik` ADD CONSTRAINT `fk_korisnik_korisnik_destinacija_1` FOREIGN KEY (`id`) REFERENCES `korisnik_destinacija` (`id_korisnik`);
ALTER TABLE `destinacija` ADD CONSTRAINT `fk_destinacija_korisnik_destinacija_1` FOREIGN KEY (`id`) REFERENCES `korisnik_destinacija` (`id_destinacija`);

