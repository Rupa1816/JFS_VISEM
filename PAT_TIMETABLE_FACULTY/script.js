let csvData = [];
const HIGHLIGHT_BATCH = "VI-SU-B2";

document.getElementById("csvFile").addEventListener("change", function () {
    Papa.parse(this.files[0], {
        header: true,
        skipEmptyLines: true,
        complete: function (result) {
            // Normalize CSV data (IMPORTANT FIX)
            csvData = result.data.map(row => ({
                DAY: row.DAY?.trim(),
                SESSION: row.SESSION?.trim(),
                COURSE: row.COURSE?.trim(),
                ROOM: row.ROOM?.trim(),
                FACULTY: row.FACULTY?.trim(),
                BATCH: row.BATCH?.trim()
            }));

            populateFacultyDropdown();
            buildAllTimetables(); // default view
        }
    });
});

function populateFacultyDropdown() {
    const select = document.getElementById("facultySelect");
    const facultySet = new Set();

    select.innerHTML = `<option value="ALL">All</option>`;

    csvData.forEach(row => {
        if (row.FACULTY) {
            facultySet.add(row.FACULTY);
        }
    });

    facultySet.forEach(faculty => {
        const option = document.createElement("option");
        option.value = faculty;
        option.textContent = faculty;
        select.appendChild(option);
    });
}

// Dropdown change → auto update timetable
document.getElementById("facultySelect").addEventListener("change", function () {
    if (this.value === "ALL") {
        buildAllTimetables();
    } else {
        buildSingleTimetable(this.value);
    }
});

function buildAllTimetables() {
    const output = document.getElementById("output");
    output.innerHTML = "";

    const facultySet = [...new Set(csvData.map(r => r.FACULTY))];

    facultySet.forEach(faculty => {
        buildTimetable(faculty, output);
    });
}

function buildSingleTimetable(faculty) {
    const output = document.getElementById("output");
    output.innerHTML = "";
    buildTimetable(faculty, output);
}

function buildTimetable(facultyId, container) {
    const days = ["MON", "TUE", "WED", "THU", "FRI", "SAT"];
    let timetable = {};
    days.forEach(day => timetable[day] = { FN: "", AN: "" });

    csvData.forEach(row => {
        if (
            row.FACULTY &&
            row.FACULTY.toLowerCase() === facultyId.toLowerCase()
        ) {
            const day = row.DAY;
            const session = row.SESSION;
            const highlight = row.BATCH === HIGHLIGHT_BATCH;

            timetable[day][session] = `
                <div class="${highlight ? 'highlight-batch' : ''}">
                    ${row.COURSE}<br>
                    Room: ${row.ROOM}<br>
                    Batch: ${row.BATCH}
                </div>
            `;
        }
    });

    const title = document.createElement("div");
    title.className = "batch-title";
    title.innerHTML = `Faculty: ${facultyId}`;
    container.appendChild(title);

    let tableHTML = `
        <table>
            <tr>
                <th>DAY</th>
                <th>FN</th>
                <th rowspan="7">L<br>U<br>N<br>C<br>H</th>
                <th>AN</th>
            </tr>
    `;

    days.forEach(day => {
        tableHTML += `
            <tr>
                <td>${day}</td>
                <td>${timetable[day].FN}</td>
                <td>${timetable[day].AN}</td>
            </tr>
        `;
    });

    tableHTML += "</table>";
    container.innerHTML += tableHTML;
}
