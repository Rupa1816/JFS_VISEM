// Auto-calculate total fee
let subjects = document.querySelectorAll(".subject");
let totalBox = document.getElementById("total");


subjects.forEach(item => {
    item.addEventListener("change", () => {
        let total = 0;

        subjects.forEach(sub => {
            if (sub.checked) {
                total += parseInt(sub.value);
            }
        });

        totalBox.innerText = "₹" + total;
    });
});

// Optional: form submit
document.getElementById("regForm").addEventListener("submit", function(e){
    if(!this.checkValidity()) return;
    e.preventDefault();
    let selectedSubjects = [];
    let totalFee = 0;
    let cnt=0;
    subjects.forEach(sub => {
        if (sub.checked) {
			cnt++;
            // Get subject name from the label text
            let subjectName = sub.parentElement.innerText.trim();
            selectedSubjects.push(cnt+"."+subjectName);
            totalFee += parseInt(sub.value);
        }
    });

    if (selectedSubjects.length === 0) {
        alert("Please select at least one subject.");
        return;
    }

    let studentName = document.getElementById("name").value;
    let outputDiv= document.getElementById("output");
    outputDiv.innerHTML = `<h3>Registration Summary for ${studentName}:</h3>
                           <p>Selected Subjects:</p>
                           ${selectedSubjects.join("<br>")}                           
                           <p><strong>Total Fee: ₹${totalFee}</strong></p>`;    
});