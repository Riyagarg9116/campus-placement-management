const menus = {
 admin:[["Overview","⌂"],["Students","♙"],["Companies & Jobs","▤"],["Applications","▣"],["Interviews","◷"],["Placement Reports","▥"],["ML Prediction","✧"]],
 student:[["My Dashboard","⌂"],["Explore Jobs","⌕"],["My Applications","▣"],["Interview Schedule","◷"],["My Profile","♙"],["ML Prediction","✧"]]
};
let role="admin",page="Overview";
// Demo data. The student preview represents Aarav Sharma only.
const currentStudent="Aarav Sharma";
let students=[["Aarav Sharma","COE","8.7"],["Diya Mehta","CSE","8.4"],["Kabir Singh","ECE","7.9"],["Ananya Gupta","COE","9.1"]];
const studentProfile={name:"Aarav Sharma",branch:"COE",cgpa:8.7,email:"aarav@college.edu",backlogs:0,graduationYear:2028,skills:"DSA, C++, Java, Python"};
// Demo job records include each opening's own eligibility criteria.
let companies=[
 ["Google","Software Engineer",7.5,"Open","COE, CSE, ECE",0,2028,"DSA, C++, Java","30 Nov 2026"],
 ["Microsoft","SDE Intern",8.0,"Open","COE, CSE",0,2028,"DSA, C++, OOP","25 Nov 2026"],
 ["Deloitte","Analyst",6.5,"Open","COE, CSE, ECE",1,2028,"Communication, Excel","28 Nov 2026"],
 ["Adobe","Product Intern",8.2,"Closed","COE, CSE",0,2028,"Problem Solving, Java","15 Nov 2026"]
];
let resumeFile=null,resumeUrl=null;
let applications=[["Aarav Sharma","Google","Software Engineer","Shortlisted"],["Diya Mehta","Microsoft","SDE Intern","Applied"],["Kabir Singh","Deloitte","Analyst","Under Review"],["Ananya Gupta","Adobe","Product Intern","Selected"]];
let interviews=[["Aarav Sharma","Google","Technical Round","12 Nov 2026"],["Diya Mehta","Microsoft","Online Assessment","14 Nov 2026"]];
const $=s=>document.querySelector(s);
function nav(){ $("#navigation").innerHTML=menus[role].map(([n,i])=>`<button class="${page===n?'active':''}" onclick="go('${n}')"><span class="nav-icon">${i}</span>${n}</button>`).join("");}
function go(p){ if(!menus[role].some(([name])=>name===p)) p=role==="admin"?"Overview":"My Dashboard"; page=p;$("#pageTitle").textContent=p;nav();render();}
function table(headers,rows,title="Records"){return `<div class="table-panel"><div class="table-tools"><h3>${title}</h3><input class="search" placeholder="Search records..." oninput="filterRows(this)"></div><table><thead><tr>${headers.map(h=>`<th>${h}</th>`).join("")}</tr></thead><tbody>${rows.length?rows.map(r=>`<tr>${r.map(x=>`<td>${x}</td>`).join("")}</tr>`).join(""):`<tr><td colspan="${headers.length}" class="empty">No records to display.</td></tr>`}</tbody></table></div>`;}
function stats(items){return `<div class="stats">${items.map(([l,v,f,ic])=>`<div class="stat"><span class="stat-icon">${ic}</span><div class="stat-label">${l}</div><div class="stat-value">${v}</div><div class="stat-foot">${f}</div></div>`).join("")}</div>`;}
function dashboard(){
 if(role==="student"){
  const mine=applications.filter(a=>a[0]===currentStudent),myInterviews=interviews.filter(a=>a[0]===currentStudent);
  return `<div class="welcome"><div><h2>Welcome back, Aarav 👋</h2><p>Your placement activity and opportunities, all in one place.</p></div><button class="btn" onclick="go('Explore Jobs')">⌕ Explore Opportunities</button><button class="btn secondary" onclick="go('My Profile')">＋ Update Profile / Resume</button></div>${stats([["Profile Completion","85%","Keep your profile updated","♙"],["Open Drives",companies.filter(c=>c[3]==="Open").length,"Currently accepting applications","▤"],["My Applications",mine.length,"Your submitted applications","▣"],["My Interviews",myInterviews.length,"Your scheduled interviews","◷"]])}<div class="dashboard-grid"><div class="panel"><div class="panel-head"><h3>My Placement Progress</h3><span class="muted">Personal overview</span></div>${[["Profile",85],["Applications",mine.length?60:0],["Interview stage",myInterviews.length?40:0],["Selection",mine.some(a=>a[3]==="Selected")?100:0]].map(([l,v])=>`<div class="bar-row"><span>${l}</span><div class="bar"><span style="width:${v}%"></span></div><b>${v}%</b></div>`).join("")}</div><div class="panel"><div class="panel-head"><h3>My Recent Updates</h3><span class="muted">Personal activity</span></div>${mine.length?mine.map(a=>`<div class="activity"><i class="activity-dot"></i><div><b>${a[1]} · ${a[2]}</b><small>Application status: ${a[3]}</small></div></div>`).join(""):`<div class="empty">You haven't applied to any jobs yet.</div>`}${myInterviews.map(a=>`<div class="activity"><i class="activity-dot"></i><div><b>${a[1]} · ${a[2]}</b><small>Interview date: ${a[3]}</small></div></div>`).join("")}</div></div><div class="notice">You are viewing your own student workspace. Other students' profiles, applications and placement records are not shown here.</div>`;
 }
 return `<div class="welcome"><div><h2>Good evening, Placement Team 👋</h2><p>Institution-wide placement activity for the academic year.</p></div><button class="btn" onclick="go('Companies & Jobs')">＋ Add a Drive</button></div>${stats([["Registered Students","1,248","+12% this semester","♙"],["Active Companies","42","8 new this month","▤"],["Applications","3,684","+18% this month","▣"],["Students Placed","386","31% placement rate","✧"]])}<div class="dashboard-grid"><div class="panel"><div class="panel-head"><h3>Placement Progress</h3><span class="muted">2026–27</span></div>${[["Applications",82],["Shortlisted",58],["Interviews",39],["Selected",31]].map(([l,v])=>`<div class="bar-row"><span>${l}</span><div class="bar"><span style="width:${v}%"></span></div><b>${v}%</b></div>`).join("")}</div><div class="panel"><div class="panel-head"><h3>Recent Activity</h3><span class="muted">Latest updates</span></div>${[["New placement drive posted","Google · Software Engineer","2 hours ago"],["Application shortlisted","Aarav Sharma · Google","Yesterday"],["Interview scheduled","Diya Mehta · Microsoft","Yesterday"],["New student registered","Riya Kapoor · COE","2 days ago"]].map(x=>`<div class="activity"><i class="activity-dot"></i><div><b>${x[0]}</b><small>${x[1]} · ${x[2]}</small></div></div>`).join("")}</div></div>${table(["STUDENT","BRANCH","CGPA"],students.map(s=>[`<div class="person"><span class="mini-avatar">${s[0].split(" ").map(x=>x[0]).join("")}</span>${s[0]}</div>`,s[1],s[2]]),"Student directory · all students")}`;
}
function form(title,fields,submit){return `<div class="panel"><div class="panel-head"><h3>${title}</h3><span class="muted">Enter the details below</span></div><form onsubmit="${submit}(event)"><div class="form-grid">${fields.map(f=>`<div class="field ${f.full?'full':''}"><label>${f.label}</label><input required placeholder="${f.placeholder||''}" type="${f.type||'text'}" ${f.max?`max="${f.max}"`:""}></div>`).join("")}</div><div class="form-actions"><button class="btn">Save details</button><button type="reset" class="btn secondary">Clear</button></div></form></div>`;}
function render(){let c=$("#content");
 if(page.includes("Dashboard")||page==="Overview"){c.innerHTML=dashboard();return;}
 if(page==="Students"&&role==="admin"){c.innerHTML=`<div class="welcome"><div><h2>Student Directory</h2><p>Manage student academic and profile details. Eligibility is calculated separately for every job opening.</p></div><button class="btn" onclick="document.querySelector('#studentForm').scrollIntoView({behavior:'smooth'})">＋ Add Student</button></div>${table(["STUDENT","BRANCH","CGPA"],students.map(s=>[s[0],s[1],s[2]]))}<div id="studentForm" style="margin-top:18px">${form("Add Student",[{label:"Full name",placeholder:"Student name"},{label:"Branch",placeholder:"COE / CSE / ECE"},{label:"CGPA",placeholder:"e.g. 8.2",type:"number",max:10},{label:"Email",placeholder:"student@college.edu",type:"email"}],"addStudent")}</div>`;return;}
 if(page==="Companies & Jobs"||page==="Explore Jobs"){
  let isStudent=role==="student",visible=companies.filter(x=>!isStudent||x[3]==="Open");
  if(isStudent){let cards=visible.map(x=>{let check=checkEligibility(x);return `<div class="job-card"><div class="job-card-top"><div><h3>${x[0]} · ${x[1]}</h3><p class="muted">Apply by ${x[8]}</p></div><span class="pill ${check.ok?"green":"amber"}">${check.ok?"Eligible":"Not eligible"}</span></div><div class="criteria-grid"><div><small>Minimum CGPA</small><b>${x[2]}</b></div><div><small>Accepted branches</small><b>${x[4]}</b></div><div><small>Max active backlogs</small><b>${x[5]}</b></div><div><small>Graduation year</small><b>${x[6]}</b></div><div class="criteria-wide"><small>Required skills</small><b>${x[7]}</b></div></div><p class="eligibility-reason">${check.reason}</p></div>`}).join("");
   c.innerHTML=`<div class="welcome"><div><h2>Explore Opportunities</h2><p>Eligibility is checked separately against each company's published criteria.</p></div></div><div class="job-list">${cards||'<div class="panel empty">No open jobs right now.</div>'}</div><div class="notice">This is a preliminary check against the criteria listed for each opening. Final shortlisting is decided by the recruiter. Keep your profile details updated.</div>`;return;
  }
  c.innerHTML=`<div class="welcome"><div><h2>Companies & Placement Drives</h2><p>Define the complete eligibility criteria separately for every job role.</p></div><button class="btn" onclick="document.querySelector('#jobForm').scrollIntoView({behavior:'smooth'})">＋ Add Opening</button></div>${table(["COMPANY","ROLE","MIN CGPA","BRANCHES","BACKLOGS","GRAD YEAR","STATUS"],visible.map(x=>[x[0],x[1],x[2],x[4],x[5],x[6],`<span class="pill ${x[3]==="Open"?"green":"gray"}">${x[3]}</span>`]),"Job openings and eligibility criteria")}<div id="jobForm" style="margin-top:18px">${form("Add Job Opening",[{label:"Company name",placeholder:"Company"},{label:"Job role",placeholder:"Software Engineer"},{label:"Minimum CGPA",placeholder:"7.5",type:"number",max:10},{label:"Application deadline",type:"date"},{label:"Accepted branches",placeholder:"COE, CSE, ECE"},{label:"Maximum active backlogs",placeholder:"0",type:"number"},{label:"Graduation year",placeholder:"2028",type:"number"},{label:"Required skills",placeholder:"DSA, C++, Java",full:true}],"addJob")}</div>`;return;
 }
 if(page==="Applications"||page==="My Applications"){let rows=role==="admin"?applications:applications.filter(a=>a[0]===currentStudent);c.innerHTML=`<div class="welcome"><div><h2>${role==="admin"?"All Applications":"My Applications"}</h2><p>${role==="admin"?"Review and track applications across all students.":"Track the status of applications you have submitted."}</p></div></div>${table([...(role==="admin"?["STUDENT"]:[]),"COMPANY","POSITION","STATUS"],rows.map(a=>[...(role==="admin"?[a[0]]:[]),a[1],a[2],`<span class="pill ${a[3]==="Selected"?"green":a[3]==="Shortlisted"?"amber":"gray"}">${a[3]}</span>`]),role==="admin"?"Institution-wide application records":"Your application records")}`;return;}
 if(page==="Interviews"||page==="Interview Schedule"){let rows=role==="admin"?interviews:interviews.filter(a=>a[0]===currentStudent);c.innerHTML=`<div class="welcome"><div><h2>${role==="admin"?"Interview Management":"My Interview Schedule"}</h2><p>${role==="admin"?"Upcoming interviews across all students.":"Only interviews scheduled for your account are shown."}</p></div></div>${table([...(role==="admin"?["STUDENT"]:[]),"COMPANY","ROUND","DATE"],rows.map(a=>[...(role==="admin"?[a[0]]:[]),a[1],a[2],a[3]]))}`;return;}
 if(page==="Placement Reports"&&role==="admin"){c.innerHTML=`<div class="welcome"><div><h2>Placement Reports</h2><p>At-a-glance placement statistics for the academic year.</p></div><button class="btn secondary" onclick="window.print()">Print report</button></div>${stats([["Total Applications","3,684","Across all active drives","▣"],["Shortlisted","1,102","29.9% of applications","♙"],["Selected","386","31% placement rate","✧"],["Companies","42","Participating recruiters","▤"]])}<div class="panel"><h3>Placement funnel</h3>${[["Applied",100],["Shortlisted",65],["Interviewed",43],["Selected",31]].map(x=>`<div class="bar-row"><span>${x[0]}</span><div class="bar"><span style="width:${x[1]}%"></span></div><b>${x[1]}%</b></div>`).join("")}</div>`;return;}
 if(page==="My Profile"&&role==="student"){c.innerHTML=`<div class="welcome"><div><h2>My Profile</h2><p>Keep your academic details updated for accurate job-wise eligibility checks.</p></div></div><div class="panel"><div class="panel-head"><h3>Student Details</h3><span class="muted">Your profile</span></div><form id="profileForm" onsubmit="saveProfile(event)"><div class="form-grid">${[{label:"Full name",value:studentProfile.name},{label:"Email",value:studentProfile.email,type:"email"},{label:"Branch",value:studentProfile.branch},{label:"CGPA",value:studentProfile.cgpa,type:"number",max:10},{label:"Active backlogs",value:studentProfile.backlogs,type:"number"},{label:"Graduation year",value:studentProfile.graduationYear,type:"number"},{label:"Skills",value:studentProfile.skills,full:true}].map(f=>`<div class="field ${f.full?'full':''}"><label>${f.label}</label><input name="${f.label}" value="${f.value}" type="${f.type||'text'}" ${f.max?`max="${f.max}"`:""} required></div>`).join("")}</div><div class="form-actions"><button class="btn">Save profile</button></div></form></div><div class="panel resume-panel"><div class="panel-head"><h3>Resume</h3><span class="muted">PDF, DOC or DOCX · Max 5 MB</span></div><label class="resume-drop" for="resumeInput"><span class="resume-symbol">↥</span><b>Upload your resume</b><span class="muted">Choose a file from your device</span><input id="resumeInput" type="file" accept=".pdf,.doc,.docx,application/pdf,application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document" onchange="handleResume(this)"></label><div id="resumeStatus" class="resume-status">${resumeFile?`Selected: ${resumeFile.name}`:"No resume uploaded yet."}</div><div class="form-actions"><button class="btn secondary" type="button" onclick="downloadResume()" ${resumeFile?"":"disabled"}>Download selected resume</button><button class="btn secondary" type="button" onclick="removeResume()" ${resumeFile?"":"disabled"}>Remove</button></div><p class="muted small-note">This preview keeps the selected file only in the current browser session. Permanent, secure storage will be connected through the Java backend.</p></div>`;return;}
 if(page==="ML Prediction"){c.innerHTML=`<div class="welcome"><div><h2>AI Placement Predictor</h2><p>${role==="admin"?"Run a placement estimate using a student's academic and skill profile.":"Estimate your placement likelihood using your academic and skill profile."}</p></div><span class="pill">Logistic Regression · ML</span></div><div class="prediction"><div class="panel"><div class="panel-head"><h3>${role==="admin"?"Student Assessment":"My Assessment"}</h3><span class="muted">10 input features</span></div><form id="mlForm"><div class="form-grid">${[{label:"CGPA",min:0,max:10,step:0.1},{label:"Internships",min:0,max:50,step:1},{label:"Projects",min:0,max:50,step:1},{label:"Workshops / Certifications",min:0,max:50,step:1},{label:"Aptitude Test Score",min:0,max:100,step:1},{label:"Soft Skills Rating",min:0,max:10,step:0.1},{label:"SSC Marks",min:0,max:100,step:1},{label:"HSC Marks",min:0,max:100,step:1}].map(f=>`<div class="field"><label>${f.label}</label><input name="${f.label}" type="number" min="${f.min}" max="${f.max}" step="${f.step}" required></div>`).join("")}<div class="field"><label>Extracurricular Activities</label><select name="Extra"><option value="1">Yes</option><option value="0">No</option></select></div><div class="field"><label>Placement Training</label><select name="Training"><option value="1">Yes</option><option value="0">No</option></select></div></div><div class="form-actions"><button class="btn">Predict placement chance</button></div></form><div class="notice">This is an ML-based estimate, not a guarantee of selection. Actual outcomes depend on company criteria and interview performance.</div></div><div class="result"><span class="eyebrow" style="color:#e6bfc2">PREDICTION RESULT</span><div class="big" id="probability">—</div><b id="predictionText">Awaiting student data</b><p id="predictionInfo">Complete the assessment to view the model's estimate.</p></div></div>`;$("#mlForm").addEventListener("submit",predict);return;}
 c.innerHTML=`<div class="panel"><h3>Page unavailable</h3><p class="empty">This section is not available for your current role.</p></div>`;
}
function filterRows(el){let q=el.value.toLowerCase();el.closest(".table-panel").querySelectorAll("tbody tr").forEach(r=>r.style.display=r.innerText.toLowerCase().includes(q)?"":"none");}
function addStudent(e){e.preventDefault();let v=[...e.target.querySelectorAll("input")].map(x=>x.value);students.push([v[0],v[1],v[2]]);go("Students");}
function addJob(e){e.preventDefault();let v=[...e.target.querySelectorAll("input")].map(x=>x.value);companies.push([v[0],v[1],Number(v[2]),"Open",v[4],Number(v[5]),Number(v[6]),v[7],v[3]]);go("Companies & Jobs");}
function saveProfile(e){e.preventDefault();let f=new FormData(e.target);studentProfile.name=f.get("Full name");studentProfile.email=f.get("Email");studentProfile.branch=f.get("Branch").toUpperCase();studentProfile.cgpa=Number(f.get("CGPA"));studentProfile.backlogs=Number(f.get("Active backlogs"));studentProfile.graduationYear=Number(f.get("Graduation year"));studentProfile.skills=f.get("Skills");alert("Profile updated in this demo preview. Permanent saving will work after backend integration.");go("My Profile");}
function checkEligibility(job){let reasons=[];if(studentProfile.cgpa<job[2])reasons.push(`CGPA ${studentProfile.cgpa} is below required ${job[2]}.`);if(!job[4].split(",").map(x=>x.trim().toUpperCase()).includes(studentProfile.branch))reasons.push(`Branch ${studentProfile.branch} is not accepted.`);if(studentProfile.backlogs>job[5])reasons.push(`Active backlogs (${studentProfile.backlogs}) exceed the allowed ${job[5]}.`);if(studentProfile.graduationYear!==Number(job[6]))reasons.push(`This role is for graduation year ${job[6]}.`);let skillList=studentProfile.skills.toLowerCase().split(",").map(x=>x.trim());let missing=job[7].split(",").map(x=>x.trim()).filter(x=>!skillList.includes(x.toLowerCase()));if(missing.length)reasons.push(`Skills to add/verify: ${missing.join(", ")}.`);return {ok:reasons.length===0,reason:reasons.length?reasons.join(" "):"Your profile matches the listed branch, CGPA, backlog, graduation-year and skill criteria."};}
function handleResume(input){let file=input.files&&input.files[0];if(!file)return;let ext=file.name.split(".").pop().toLowerCase();if(!["pdf","doc","docx"].includes(ext)){alert("Please upload a PDF, DOC or DOCX file.");input.value="";return;}if(file.size>5*1024*1024){alert("Resume size must be 5 MB or less.");input.value="";return;}if(resumeUrl)URL.revokeObjectURL(resumeUrl);resumeFile=file;resumeUrl=URL.createObjectURL(file);$("#resumeStatus").textContent="Selected: "+file.name+" ("+(file.size/1024/1024).toFixed(2)+" MB)";document.querySelectorAll(".resume-panel button").forEach(b=>b.disabled=false);}
function downloadResume(){if(resumeUrl&&resumeFile){let a=document.createElement("a");a.href=resumeUrl;a.download=resumeFile.name;a.click();}}
function removeResume(){if(resumeUrl)URL.revokeObjectURL(resumeUrl);resumeUrl=null;resumeFile=null;let input=$("#resumeInput");if(input)input.value="";let status=$("#resumeStatus");if(status)status.textContent="No resume uploaded yet.";document.querySelectorAll(".resume-panel button").forEach(b=>b.disabled=true);}

async function predict(e) {
    e.preventDefault();

    let f = new FormData(e.target);

    let d = {
        "CGPA": Number(f.get("CGPA")),
        "Internships": Number(f.get("Internships")),
        "Projects": Number(f.get("Projects")),
        "Workshops/Certifications": Number(f.get("Workshops / Certifications")),
        "AptitudeTestScore": Number(f.get("Aptitude Test Score")),
        "SoftSkillsRating": Number(f.get("Soft Skills Rating")),
        "ExtracurricularActivities": f.get("Extra") === "1" ? "Yes" : "No",
        "PlacementTraining": f.get("Training") === "1" ? "Yes" : "No",
        "SSC_Marks": Number(f.get("SSC Marks")),
        "HSC_Marks": Number(f.get("HSC Marks"))
    };

    try {
        let r = await fetch("http://127.0.0.1:5000/predict", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(d)
        });

        let x = await r.json();

        if (!r.ok) {
            throw Error(x.error || "API returned " + r.status);
        }

        let val = Number(x.placement_probability);

        if (!Number.isFinite(val)) {
            throw Error("Invalid probability received");
        }

        $("#probability").textContent = val.toFixed(1) + "%";

        $("#predictionText").textContent = x.prediction;

        $("#predictionInfo").textContent =
            "Prediction returned by your Logistic Regression model.";

    } catch (err) {
        $("#probability").textContent = "API error";
        $("#predictionText").textContent = "Could not connect to ML service";
        $("#predictionInfo").textContent = err.message;
        console.error("ML API Error:", err);
    }
}

$("#role").addEventListener("change",e=>{role=e.target.value;page=role==="admin"?"Overview":"My Dashboard";$("#userName").textContent=role==="admin"?"Admin Desk":"Aarav Sharma";$("#userRole").textContent=role==="admin"?"Placement Office":"Student Portal";$("#userName").previousElementSibling.textContent=role==="admin"?"AD":"AS";$(".avatar.small").textContent=role==="admin"?"AD":"AS";go(page);});
nav();render();
