//https://medium.com/@yasaswini.gaddam21/wagner-fischer-algorithm-minimum-edit-distance-4e61bba9b656
export function wagnerFischer(s1, s2) {
    //Because the whole 
    s1 = " " + s1;
    s2 = " " + s2;
    
    const m = s1.length;
    const n = s2.length;

    //Initialize an empty array where our solutions
    let dp = Array.from({ length: m }, () => new Array(n).fill(0));

    //this is the case if we drop all chars in s2
    //And creates the s1 from an empty string
    for(let i = 0; i < m; i++){
        dp[i][0] = i;
    }

    //Making an empty string cost the same as deleting every char in the string
    for(let j = 0; j < n; j++){
        dp[0][j] = j;
    }

    for(let i = 1; i < m; i++){
        for(let j = 1; j < n; j++){
            
            dp[i][j] = Math.min(
                //Deleting a char
                dp[i - 1][j] + 1,
                //Inserting a char
                dp[i][j - 1] + 1, //Maybe we should make this cheaper meaning that it would help auto complete longer words
                //sustituting a char //If the chars are identical we don't have to sub, meaning the cost is free
                dp[i - 1][j - 1] + (s1[i] === s2[j] ? 0 : 1));
        }
    }

    //if we change all chars to get s2;
    const maxChanges = Math.max(m,n) - 1;
    //The closer the values are to 1 the more similar they are.

    //This allows longer strings to have more mistakes when we verdict them.
    return 1 - dp[m-1][n-1] / maxChanges;
}

//Enables search functionality on toolname, toolURL and tooltags
export function fuzzySearch(input, tool){
    if(tool.url.includes("http://")) {
        tool.url = tool.url.replace("http://", "");
    } else if (tool.url.includes("https://")) {
        tool.url = tool.url.replace("https://", "");
    }

    //Firstly check if it matches name
    if (tool.name.toLowerCase().startsWith(input.toLowerCase()) || wagnerFischer(input.toLowerCase(), tool.name.toLowerCase()) > 0.55){
        return true;
    }
    //Then url. It we allow it through with a lower score because it is more difficult getting right.
    if (tool.url.toLowerCase().includes(input.toLowerCase()) || wagnerFischer(input.toLowerCase(), tool.url.toLowerCase()) > 0.30){
        return true;
    }

    if (!tool.is_personal) { // we only check for tags if it is a company tool
        // Lastly we iterate through all tags on the tool if we get a good value on one we have a match
        for (let i = 0; i < tool.tags.length; i++){
            if (tool.tags[i].toLowerCase().startsWith(input.toLowerCase()) || wagnerFischer(input.toLowerCase(), tool.tags[i].toLowerCase()) > 0.55){
                return true;
            }
        }
    }
    
    // if no match return false
    return false;
}

export function fuzzySearchTags(input, tagValue){
    if(tagValue.toLowerCase().startsWith(input.toLowerCase()) || wagnerFischer(input.toLowerCase(), tagValue.toLowerCase()) > 0.55){
        return true;
    }

    return false;
}