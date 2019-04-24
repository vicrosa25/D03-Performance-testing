start transaction;

use `acme-hacker-rank`;

revoke all privileges on `acme-hacker-rank`.* from 'acme-user'@'%';

revoke all privileges on `acme-hacker-rank`.* from 'acme-manager'@'%';

drop user 'acme-user'@'%';

drop user 'acme-manager'@'%';

drop database `acme-hacker-rank`;

commit;