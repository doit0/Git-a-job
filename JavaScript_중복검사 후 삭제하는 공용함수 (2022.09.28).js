// 매개변수로 들어오는 Array 객체 안의 중복 제거하기

function del_clone_of_arr(arr){
    if(arr==null){return;}
    
    try{    // 더블반복문 → 유효성 체크
        for(var i=0 ; i<arr.length-1; i++){
            for(var j=i+1; j<arr.length; j++){
                
                if(arr[i]==arr[j]){
                    // j번째 데이터만 삭제하기
                    arr.splice(j, 1);
                    // 연속으로 같은 데이터가 나왔을 때 횟수반환,
                    // 계속 중복 체크&삭제할 수 있게 j--; 넣어주기
                    j--;
                }
            }
        }

    }
    catch(e){
        alert(" del_clone_of_arr 함수 호출시 예외 발생" + e.message)
    }
}




// 매개변수로 들어오는 Array 객체 안의 데이터 복사 후 중복 제거한 Array 객체 return 하기

function copyArr_del_clone_of_arr (arr){
    // 만약 arr 안의 데이터가 null이라면 비어있는 Array 객체 리턴.
   
    
    try{
            if( arr == null){return [];}
            //복사한 Array 객체 전체를 변수 clone_arr에 저장함
            var clone_arr = arr.slice(0)
            
            del_clone_of_arr(clone_arr)

            return clone_arr;
    }
    catch(e){
        alert(" copyArr_del_clone_of_arr 함수 호출시 예외 발생" + e.message)
    }
}   