--This ensures that our URL's looks like they should on frontend 
UPDATE `P3`.`tool` SET `url` = '$USER$.greathippydev.co.uk' WHERE (`id` = '3');
UPDATE `P3`.`tool` SET `url` = '$USER$.stage.happytiger.co.uk' WHERE (`id` = '4');
UPDATE `P3`.`tool` SET `url` = '$USER$.lupinsdev.dk' WHERE (`id` = '6');
UPDATE `P3`.`tool` SET `url` = '$USER$.stage.spilnu.dk' WHERE (`id` = '7');