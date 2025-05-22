/**
 * Main JavaScript file for HaxQuarium
 */

// Initialize tooltips and popovers
document.addEventListener('DOMContentLoaded', function() {
    // Initialize Bootstrap tooltips
    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    var tooltipList = tooltipTriggerList.map(function(tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });
    
    // Initialize Bootstrap popovers
    var popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'));
    var popoverList = popoverTriggerList.map(function(popoverTriggerEl) {
        return new bootstrap.Popover(popoverTriggerEl);
    });
});

/**
 * Function to submit a vulnerability flag
 * @param {string} flagUuid - The UUID of the flag to submit
 * @param {function} callback - Callback function to handle the response
 */
function submitFlag(flagUuid, callback) {
    fetch('/vulnerabilities/submit-flag', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: 'flagUuid=' + encodeURIComponent(flagUuid)
    })
    .then(response => response.json())
    .then(data => {
        if (callback && typeof callback === 'function') {
            callback(data);
        }
    })
    .catch(error => {
        console.error('Error submitting flag:', error);
        if (callback && typeof callback === 'function') {
            callback({ success: false, message: 'Error submitting flag: ' + error.message });
        }
    });
}

/**
 * Function to show a success message
 * @param {string} message - The message to display
 * @param {string} containerId - The ID of the container to append the message to
 */
function showSuccessMessage(message, containerId) {
    const container = document.getElementById(containerId);
    if (container) {
        const alertDiv = document.createElement('div');
        alertDiv.className = 'alert alert-success alert-dismissible fade show';
        alertDiv.role = 'alert';
        
        alertDiv.innerHTML = `
            ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        `;
        
        container.appendChild(alertDiv);
        
        // Auto-dismiss after 5 seconds
        setTimeout(() => {
            const bsAlert = new bootstrap.Alert(alertDiv);
            bsAlert.close();
        }, 5000);
    }
}

/**
 * Function to show an error message
 * @param {string} message - The message to display
 * @param {string} containerId - The ID of the container to append the message to
 */
function showErrorMessage(message, containerId) {
    const container = document.getElementById(containerId);
    if (container) {
        const alertDiv = document.createElement('div');
        alertDiv.className = 'alert alert-danger alert-dismissible fade show';
        alertDiv.role = 'alert';
        
        alertDiv.innerHTML = `
            ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        `;
        
        container.appendChild(alertDiv);
        
        // Auto-dismiss after 5 seconds
        setTimeout(() => {
            const bsAlert = new bootstrap.Alert(alertDiv);
            bsAlert.close();
        }, 5000);
    }
}

/**
 * Function to update the user's points display
 * @param {number} points - The new points value
 */
function updatePointsDisplay(points) {
    const pointsElements = document.querySelectorAll('.user-points');
    pointsElements.forEach(element => {
        element.textContent = points;
    });
}

/**
 * Function to highlight code syntax
 * @param {string} codeElementId - The ID of the code element to highlight
 */
function highlightCode(codeElementId) {
    const codeElement = document.getElementById(codeElementId);
    if (codeElement) {
        const code = codeElement.textContent;
        
        // Simple syntax highlighting for demonstration purposes
        const highlightedCode = code
            .replace(/\b(function|return|if|else|for|while|var|let|const|class|import|export|from|try|catch|throw)\b/g, '<span class="keyword">$1</span>')
            .replace(/(".*?"|'.*?'|`.*?`)/g, '<span class="string">$1</span>')
            .replace(/(\/\/.*)/g, '<span class="comment">$1</span>')
            .replace(/\b(\d+)\b/g, '<span class="number">$1</span>');
        
        codeElement.innerHTML = highlightedCode;
    }
}

/**
 * Function to show a vulnerability hint
 * @param {string} hintId - The ID of the hint element to show
 */
function showHint(hintId) {
    const hintElement = document.getElementById(hintId);
    if (hintElement) {
        hintElement.style.display = 'block';
    }
}

/**
 * Function to check if an XSS vulnerability has been exploited
 * This is intentionally vulnerable for training purposes
 */
function checkXssVulnerability() {
    // Get the URL parameters
    const urlParams = new URLSearchParams(window.location.search);
    const xssParam = urlParams.get('xss');
    
    // If the xss parameter exists, render it (intentionally vulnerable)
    if (xssParam) {
        const xssContainer = document.getElementById('xss-container');
        if (xssContainer) {
            xssContainer.innerHTML = xssParam;
        }
    }
}

// Call the XSS check function when the page loads
document.addEventListener('DOMContentLoaded', checkXssVulnerability);
