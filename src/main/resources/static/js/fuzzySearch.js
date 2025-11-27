//https://medium.com/@yasaswini.gaddam21/wagner-fischer-algorithm-minimum-edit-distance-4e61bba9b656
function wagnerFischer(s1, s2) {
    //Because the whole 
    s1 = " " + s1;
    s2 = " " + s2;
    console.log(s1);
    console.log(s2);
    
    const m = s1.length;
    const n = s2.length;

    //Initialize an empty array where our solutions
    let dp = Array.from({ length: m }, () => new Array(n).fill(0));

    //this is the case if we drop all chars in s2
    //And creates the s1 from an empty string
    for(let i = 0; i < m; i++){
        dp[i][0] = i;
    }

    //Making an empty string cost the same as deletig everychar in the string
    for(let j = 0; j < n; j++){
        dp[0][j] = j;
    }


    for(let i = 1; i < m; i++){
        for(let j = 1; j < n; j++){

            //If we have to substitute a char
            let substitutionCost = 1;

            //If the chars are identical we don't have to sub, meaning the cost is free
            if (s1[i] == s2[j]){
               substitutionCost = 0; 
            } 

            dp[i][j] = Math.min(
                //Deleting a char
                dp[i - 1][j] + 1, 
                //Inserting a char
                dp[i][j - 1] + 1, 
                //sustituting a char 
                dp[i - 1][j - 1] + substitutionCost);
        }
    }

    //if we change all chars to get s2;
    const maxChanges = Math.max(m,n) - 1;
    //The closer the values are to 1 the more similar they are.
    return 1 - dp[m-1][n-1] / maxChanges;
}

export function fuzzySearch(s1, s2){
    const similarityScore = wagnerFischer(s1,s2);

    if (similarityScore > 0.66){
        return true;
    }
}

