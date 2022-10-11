
// 매개변수로 들어오는 객체가 Array 객체이고
// 그 안에 배열변수가 1개 이상이면 true 리턴하는 공용함수 is_Array 선언하기

function is_Array ( obj ){
    
    try{        /// 예외 X
        return( obj.constructor.name=="Array" && obj.length>0);

    } catch(e){ /// 예외 발생시 무조건 false 리턴하기.
        return false;
    }

}

// 매개변수로 들어오는 데이터가 undefined거나 
// null 이면 true 리턴하는 공용함수 is_Unde_or_Null 선언하기

function is_Unde_or_Null ( data ){

    // 만약 obj 안에 undefined 가 있다면 obj.constructor.name == "Array" 에서 에러가 남. ( obj 가 Array가 아니니까...)
         return( data == undefined || data == null );
}

 
 
// 매개변수로 들어오는 데이터가 문자면서
// 실질적인 문자데이터가 (영/한/특수기호/숫자 등) 1개 이상이면 true 리턴
// 혹은
// 길이가 없거나 공백으로만 이루어져 있거나 문자가 아닌 경우면 false 리턴
// 하는 공용함수 is_ValidStr 선언하기


function is_ValidStr(str){
    return typeof(str)=="string" 
    && str!="" 
    && str.split(" ").join("")!="";     // typeof(): 내장함수, 변수 안의 데이터의 자료형을 리턴함. 메소드를 사용.

    /*
    
    예외처리를 역 이용하여 아래처럼 코딩하는 것도 가능.
    
  try { 
  
    // 길이가 없거나, 공백으로만 이루어져있거나(공백 외의 데이터가 1개 이상)
    return typeof(str)=="string" 
    && str!="" 
    && str.split(" ").join("")!="";
    
    }
     catch(e){
        return false;
    }

*/

                    // 내가 한 망한코딩...
                    // if ((str == "" && str == " " && is_Unde_or_Null(str) ) == false ){
                    //     return true; 
                    // }
                    // else { return false;}

}
