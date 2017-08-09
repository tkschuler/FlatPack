// author: Tristan Schuler
// sources: updated_TableTop.svg
//           updated_Leg1.svg
//           updated_Leg2.svg
// date: Thu Jul 27 2017 14:53:17 GMT-0400 (EDT)

var table = new Array();

function main(params) {
  var cag0 = new CAG();
  var cag00 = new CAG();
  var cag001 = new CSG.Path2D([[0,0]],false);
  cag001 = cag001.appendPoint([0,-14.111109999999998]);
  cag001 = cag001.appendPoint([-5.644443999999999,-14.111109999999998]);
  cag001 = cag001.appendPoint([-5.644443999999999,-32.92592333333333]);
  cag001 = cag001.appendPoint([0,-32.92592333333333]);
  cag001 = cag001.appendPoint([0,-51.74073666666666]);
  cag001 = cag001.appendPoint([-5.644443999999999,-51.74073666666666]);
  cag001 = cag001.appendPoint([-5.644443999999999,-70.55555]);
  cag001 = cag001.appendPoint([0,-70.55555]);
  cag001 = cag001.appendPoint([0,-70.55555]);
  cag001 = cag001.appendPoint([0,-84.66666]);
  cag001 = cag001.appendPoint([197.55553999999998,-84.66666]);
  cag001 = cag001.appendPoint([197.55553999999998,-70.55555]);
  cag001 = cag001.appendPoint([208.84442799999997,-70.55555]);
  cag001 = cag001.appendPoint([208.84442799999997,-64.28394555555555]);
  cag001 = cag001.appendPoint([197.55553999999998,-64.28394555555555]);
  cag001 = cag001.appendPoint([197.55553999999998,-58.0123411111111]);
  cag001 = cag001.appendPoint([208.84442799999997,-58.0123411111111]);
  cag001 = cag001.appendPoint([208.84442799999997,-51.740736666666656]);
  cag001 = cag001.appendPoint([197.55553999999998,-51.740736666666656]);
  cag001 = cag001.appendPoint([197.55553999999998,-45.46913222222221]);
  cag001 = cag001.appendPoint([208.84442799999997,-45.46913222222221]);
  cag001 = cag001.appendPoint([208.84442799999997,-39.197527777777765]);
  cag001 = cag001.appendPoint([197.55553999999998,-39.197527777777765]);
  cag001 = cag001.appendPoint([197.55553999999998,-32.925923333333316]);
  cag001 = cag001.appendPoint([208.84442799999997,-32.925923333333316]);
  cag001 = cag001.appendPoint([208.84442799999997,-26.654318888888874]);
  cag001 = cag001.appendPoint([197.55553999999998,-26.654318888888874]);
  cag001 = cag001.appendPoint([197.55553999999998,-20.382714444444428]);
  cag001 = cag001.appendPoint([208.84442799999997,-20.382714444444428]);
  cag001 = cag001.appendPoint([208.84442799999997,-14.111109999999984]);
  cag001 = cag001.appendPoint([197.55553999999998,-14.111109999999984]);
  cag001 = cag001.appendPoint([197.55553999999998,-14.111109999999998]);
  cag001 = cag001.appendPoint([197.55553999999998,0]);
  cag001 = cag001.close();
  cag001 = cag001.innerToCAG();
  cag00 = cag00.union(cag001);
  cag0 = cag0.union(cag00);
  cag0 = linear_extrude( { height: 10 }, cag0 );
  cag0 = translate([-100,40,100],cag0);
  
  table[0] = cag0.scale(2/3);
 
  var cag1 = new CAG();
  var cag11 = new CAG();
  var cag111 = new CSG.Path2D([[0,0]],false);
  cag111 = cag111.appendPoint([0,-84.66666]);
  cag111 = cag111.appendPoint([56.44443999999999,-84.66666]);
  cag111 = cag111.appendPoint([56.44443999999999,0]);
  cag111 = cag111.appendPoint([50.17283555555555,0]);
  cag111 = cag111.appendPoint([50.17283555555555,11.288887999999998]);
  cag111 = cag111.appendPoint([43.9012311111111,11.288887999999998]);
  cag111 = cag111.appendPoint([43.9012311111111,0]);
  cag111 = cag111.appendPoint([37.62962666666666,0]);
  cag111 = cag111.appendPoint([37.62962666666666,11.288887999999998]);
  cag111 = cag111.appendPoint([31.35802222222221,11.288887999999998]);
  cag111 = cag111.appendPoint([31.35802222222221,0]);
  cag111 = cag111.appendPoint([25.086417777777765,0]);
  cag111 = cag111.appendPoint([25.086417777777765,11.288887999999998]);
  cag111 = cag111.appendPoint([18.81481333333332,11.288887999999998]);
  cag111 = cag111.appendPoint([18.81481333333332,0]);
  cag111 = cag111.appendPoint([12.543208888888877,0]);
  cag111 = cag111.appendPoint([12.543208888888877,11.288887999999998]);
  cag111 = cag111.appendPoint([6.271604444444433,11.288887999999998]);
  cag111 = cag111.appendPoint([6.271604444444433,0]);
  cag111 = cag111.close();
  cag111 = cag111.innerToCAG();
  cag11 = cag11.union(cag111);
  cag1 = cag1.union(cag11);

  cag1 = linear_extrude( { height: 10 }, cag1 );
  cag1= cag1.setColor(css2rgb('dodgerblue'));
  
  cag1 = rotate([90,0,90],cag1);
  cag1 = translate([98,-30.6,99.3],cag1);
  
  table[1] = cag1.scale(2/3);
  
  var cag2 = new CAG();
  var cag22 = new CAG();
  var cag222 = new CSG.Path2D([[0,0]],false);
  cag222 = cag222.appendPoint([0,-84.66666]);
  cag222 = cag222.appendPoint([56.44443999999999,-84.66666]);
  cag222 = cag222.appendPoint([56.44443999999999,0]);
  cag222 = cag222.appendPoint([37.62962666666666,0]);
  cag222 = cag222.appendPoint([37.62962666666666,5.644443999999999]);
  cag222 = cag222.appendPoint([18.814813333333326,5.644443999999999]);
  cag222 = cag222.appendPoint([18.814813333333326,0]);
  cag222 = cag222.close();
  cag222 = cag222.innerToCAG();
  cag22 = cag22.union(cag222);
  cag2 = cag2.union(cag22);
  
  cag2 = linear_extrude( { height: 10 }, cag2 );
  cag2= cag2.setColor(css2rgb('yellow'));
  cag2 = rotate([90,0,90],cag2);
  cag2 = translate([-108,-30,100],cag2);
  
  table[2] = cag2.scale(2/3);
  
return table;
}